package com.gtombul.siteyonetimi.service.talep;

import com.gtombul.siteyonetimi.config.SecurityAuditorAware; // Projenizde bu isimde olmalı
import com.gtombul.siteyonetimi.dto.talep.*;
import com.gtombul.siteyonetimi.mapper.talep.TalepMapper;
import com.gtombul.siteyonetimi.model.bina.Daire;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.GorevDurum;
import com.gtombul.siteyonetimi.model.enums.TalepDurum;
import com.gtombul.siteyonetimi.model.personel.Personel;
import com.gtombul.siteyonetimi.model.talep.Gorev;
import com.gtombul.siteyonetimi.model.talep.Talep;
import com.gtombul.siteyonetimi.model.talep.TalepGuncelleme;
import com.gtombul.siteyonetimi.model.talep.TalepKategori;
import com.gtombul.siteyonetimi.model.user.UserEntity;
import com.gtombul.siteyonetimi.repository.bina.DaireRepository;
import com.gtombul.siteyonetimi.repository.personel.PersonelRepository;
import com.gtombul.siteyonetimi.repository.talep.GorevRepository;
import com.gtombul.siteyonetimi.repository.talep.TalepGuncellemeRepository;
import com.gtombul.siteyonetimi.repository.talep.TalepKategoriRepository;
import com.gtombul.siteyonetimi.repository.talep.TalepRepository;
import com.gtombul.siteyonetimi.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional // Servis katmanında tüm metotlar transaction içinde çalışsın (Best practice)
public class TalepServiceImpl implements TalepService {

    // Repositories (Talep Modülü)
    private final TalepRepository talepRepository;
    private final TalepKategoriRepository talepKategoriRepository;
    private final GorevRepository gorevRepository;
    private final TalepGuncellemeRepository talepGuncellemeRepository;

    // Repositories (Diğer Modüller - İlişki kurmak için)
    // Projenizdeki DaireRepository, UserRepository, PersonelRepository'yi inject edin.
    private final DaireRepository daireRepository;
    private final UserRepository userRepository;
    private final PersonelRepository personelRepository;

    // Mapper (BaseMapper değil, özel TalepMapper)
    private final TalepMapper talepMapper;

    // Güvenlikten aktif kullanıcıyı almak için (BaseEntityListener'daki gibi)
    private final SecurityAuditorAware securityAuditorAware;

    @Override
    public TalepDetayDto talepOlustur(TalepOlusturRequestDto requestDto) {
        log.info("Yeni talep oluşturuluyor: {}", requestDto.konu());

        // 1. İlişkili varlıkları bul (Daire, Kategori, Kullanıcı)
        UserEntity olusturanKullanici = aktifKullaniciyiGetir();
        Daire daire = daireRepository.findByUuidAndDurum(requestDto.daireUuid(), Durum.AKTIF)
                .orElseThrow(() -> new EntityNotFoundException("Daire bulunamadı: " + requestDto.daireUuid()));
        TalepKategori kategori = talepKategoriRepository.findByUuidAndDurum(requestDto.kategoriUuid(), Durum.AKTIF)
                .orElseThrow(() -> new EntityNotFoundException("Talep Kategori bulunamadı: " + requestDto.kategoriUuid()));

        // 2. Yeni Talep Entity'si oluştur
        Talep talep = Talep.builder()
                .konu(requestDto.konu())
                .aciklama(requestDto.aciklama())
                .talepOncelik(requestDto.talepOncelik())
                .talepDurum(TalepDurum.ACIK) // Her zaman 'Açık' başlar
                .olusturanKullanici(olusturanKullanici)
                .daire(daire)
                .kategori(kategori)
                .build();

        // BaseEntity alanları (id, uuid, durum, olusturan, olusturmaTarihi)
        // BaseEntityListener tarafından otomatik doldurulacak.

        Talep kaydedilenTalep = talepRepository.save(talep);

        // 3. İlk güncelleme kaydını (talep açılışı) otomatik oluştur
        TalepGuncelleme ilkGuncelleme = TalepGuncelleme.builder()
                .talep(kaydedilenTalep)
                .kullanici(olusturanKullanici)
                .aciklama("Talep oluşturuldu.")
                .yeniDurum(TalepDurum.ACIK.getValue()) // Enum'un string 'value' değeri
                .build();
        talepGuncellemeRepository.save(ilkGuncelleme);

        log.info("Talep başarıyla oluşturuldu: {}", kaydedilenTalep.getUuid());

        // 4. Kaydedilen talebin tam detayını N+1 optimizasyonuyla çekip döndür
        return getTalepDetay(kaydedilenTalep.getUuid());
    }

    @Override
    @Transactional(readOnly = true) // Sadece okuma işlemi
    public TalepDetayDto getTalepDetay(UUID talepUuid) {
        log.debug("Talep detayı getiriliyor: {}", talepUuid);
        Talep talep = talepBul(talepUuid); // N+1 optimized
        return talepMapper.toTalepDetayDto(talep);
    }

    @Override
    public TalepDetayDto talepDurumGuncelle(UUID talepUuid, TalepDurumGuncelleRequestDto requestDto) {
        log.info("Talep {} durumu güncelleniyor: {}", talepUuid, requestDto.yeniDurum());

        Talep talep = talepBul(talepUuid);
        UserEntity kullanici = aktifKullaniciyiGetir(); // İşlemi yapan yönetici/personel

        TalepDurum eskiDurum = talep.getTalepDurum();
        if (eskiDurum == requestDto.yeniDurum()) {
            log.warn("Talep durumu zaten {}. İşlem yapılmadı.", eskiDurum);
            return talepMapper.toTalepDetayDto(talep); // Değişiklik yok
        }

        // 1. Durumu güncelle
        talep.setTalepDurum(requestDto.yeniDurum());
        // guncelleyen ve guncellemeTarihi BaseEntityListener'dan gelecek
        talepRepository.save(talep);

        // 2. Durum değişikliğini logla (TalepGuncelleme)
        TalepGuncelleme guncelleme = TalepGuncelleme.builder()
                .talep(talep)
                .kullanici(kullanici)
                .aciklama(requestDto.aciklama())
                .eskiDurum(eskiDurum.getValue())
                .yeniDurum(requestDto.yeniDurum().getValue())
                .build();
        talepGuncellemeRepository.save(guncelleme);

        log.info("Talep {} durumu {} -> {} olarak güncellendi.", talepUuid, eskiDurum, requestDto.yeniDurum());
        return getTalepDetay(talepUuid); // Güncel halini çek
    }

    @Override
    public TalepDetayDto gorevAta(UUID talepUuid, GorevAtaRequestDto requestDto) {
        log.info("Talep {} için görev atanıyor: Personel {}", talepUuid, requestDto.personelUuid());

        Talep talep = talepBul(talepUuid);
        if (talep.getGorev() != null && talep.getGorev().getDurum() == Durum.AKTIF) {
            log.warn("Talep {} için zaten aktif bir görev (UUID: {}) mevcut.", talepUuid, talep.getGorev().getUuid());
            throw new IllegalStateException("Bu talebe zaten bir görev atanmış.");
        }

        UserEntity yonetici = aktifKullaniciyiGetir();
        Personel personel = personelRepository.findByUuidAndDurum(requestDto.personelUuid(), Durum.AKTIF)
                .orElseThrow(() -> new EntityNotFoundException("Personel bulunamadı: " + requestDto.personelUuid()));

        // 1. Görevi oluştur
        Gorev gorev = Gorev.builder()
                .talep(talep)
                .atananPersonel(personel)
                .gorevDurum(GorevDurum.ATANDI)
                .yoneticiNotu(requestDto.yoneticiNotu())
                .planlananBaslangicTarihi(requestDto.planlananBaslangicTarihi())
                .planlananBitisTarihi(requestDto.planlananBitisTarihi())
                .build();

        gorevRepository.save(gorev);

        // 2. Talep durumunu da güncelle (örn: "PLANLANDI" veya "İŞLEMDE")
        TalepDurum eskiDurum = talep.getTalepDurum();
        talep.setTalepDurum(TalepDurum.PLANLANDI);
        talepRepository.save(talep);

        // 3. Durum değişikliğini logla
        TalepGuncelleme guncelleme = TalepGuncelleme.builder()
                .talep(talep)
                .kullanici(yonetici)
                .aciklama(personel.getKisi().getAd() + " " + personel.getKisi().getSoyad() + " kişisine görev atandı.") // Personel ad/soyadına göre güncelle
                .eskiDurum(eskiDurum.getValue())
                .yeniDurum(TalepDurum.PLANLANDI.getValue())
                .build();
        talepGuncellemeRepository.save(guncelleme);

        log.info("Görev başarıyla atandı. Görev UUID: {}", gorev.getUuid());
        return getTalepDetay(talepUuid);
    }

    @Override
    public TalepDetayDto talepYorumEkle(UUID talepUuid, TalepYorumEkleRequestDto requestDto) {
        log.info("Talep {} için yorum ekleniyor.", talepUuid);

        Talep talep = talepBul(talepUuid);
        UserEntity kullanici = aktifKullaniciyiGetir();

        TalepGuncelleme yorum = TalepGuncelleme.builder()
                .talep(talep)
                .kullanici(kullanici)
                .aciklama(requestDto.aciklama())
                // eskiDurum ve yeniDurum null olacak (bu bir yorum)
                .build();
        talepGuncellemeRepository.save(yorum);

        log.info("Yorum eklendi, talep detayı tekrar çekiliyor.");
        return getTalepDetay(talepUuid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TalepListeDto> getKullanicininTalepleri() {
        UserEntity kullanici = aktifKullaniciyiGetir();
        log.debug("Kullanıcı {} talepleri getiriliyor.", kullanici.getUuid());

        List<Talep> talepler = talepRepository.findAllByOlusturanKullaniciUuidAndDurum(kullanici.getUuid(), Durum.AKTIF);
        return talepMapper.toTalepListeDtoList(talepler);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TalepListeDto> getPersonelinGorevleri(UUID personelUuid) {
        // Güvenlik kontrolü: Aktif kullanıcı ya bu personelin kendisi ya da yönetici olmalı (burada yapılabilir)
        log.debug("Personel {} görevleri getiriliyor.", personelUuid);

        List<Talep> talepler = talepRepository.findAllByGorevAtananPersonelUuidAndDurum(personelUuid, Durum.AKTIF);
        return talepMapper.toTalepListeDtoList(talepler);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TalepListeDto> listTumTalepler(Pageable pageable) {
        log.debug("Tüm talepler listeleniyor, Sayfa: {}", pageable.getPageNumber());

        // (Buraya BaseServiceImpl'deki gibi Specification/filtreleme eklenebilir)
        Page<Talep> talepSayfasi = talepRepository.findAllByDurum(Durum.AKTIF, pageable);
        return talepMapper.toTalepListeDtoPage(talepSayfasi);
    }

    @Override
    public void deleteTalep(UUID talepUuid) {
        log.warn("Talep {} (soft) siliniyor...", talepUuid);
        Talep talep = talepBul(talepUuid);

        // BaseServiceImpl'deki delete metodunun manuel hali (Sadece durumu değiştiririz)
        // BaseEntityListener'daki @PreRemove fiziksel silmeyi zaten engelliyor,
        // ancak biz yine de 'durum'u SILINDI yapmalıyız.

        // BaseEntityListener @PreRemove'ü tetiklemeyecek çünkü repository.delete() çağırmıyoruz.
        // Soft delete'i manuel yapmalıyız.

        talep.setDurum(Durum.SILINDI);

        // İlişkili görev varsa onu da (soft) sil
        if (talep.getGorev() != null) {
            talep.getGorev().setDurum(Durum.SILINDI);
            // gorevRepository.save(talep.getGorev()); // Cascade.ALL olduğu için buna gerek yok
        }

        talepRepository.save(talep); // Talep'i ve cascade ile Görev'i günceller
        log.info("Talep {} başarıyla (soft) silindi.", talepUuid);
    }


    // --- Yardımcı Metotlar ---

    /**
     * Talebi N+1 optimizasyonu ile (EntityGraph kullanarak) bulan metot.
     */
    private Talep talepBul(UUID uuid) {
        return talepRepository.findByUuidAndDurum(uuid, Durum.AKTIF)
                .orElseThrow(() -> new EntityNotFoundException("Talep bulunamadı: " + uuid));
    }

    /**
     * Güvenlik bağlamından (JWT Token) aktif kullanıcıyı çeker.
     */
    private UserEntity aktifKullaniciyiGetir() {
        // SecurityAuditorAware'dan (projenizdeki) mevcut kullanıcı ID'sini al
        Long kullaniciId = Long.valueOf(securityAuditorAware.getCurrentAuditor()
                .orElseThrow(() -> new IllegalStateException("Güvenlik bağlamında kullanıcı bulunamadı.")));

        // Kullanıcıyı veritabanından çek
        return userRepository.findById(kullaniciId)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + kullaniciId));
    }
}