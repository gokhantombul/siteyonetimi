package com.gtombul.siteyonetimi.service.duyuru;

import com.gtombul.siteyonetimi.dto.duyuru.DuyuruCreateRequest;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruResponse;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruUpdateRequest;
import com.gtombul.siteyonetimi.mapper.duyuru.DuyuruMapper;
import com.gtombul.siteyonetimi.model.dosya.Dosya;
import com.gtombul.siteyonetimi.model.duyuru.Duyuru;
import com.gtombul.siteyonetimi.model.duyuru.DuyuruDosyasi;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.duyuru.DuyuruRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import com.gtombul.siteyonetimi.service.dosya.DosyaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DuyuruServiceImpl extends BaseServiceImpl<Duyuru, DuyuruResponse, Long> implements DuyuruService {

    private final DosyaService dosyaServisi;

    public DuyuruServiceImpl(DuyuruRepository repository,
                             DuyuruMapper mapper, // <-- Bu 'mapper' artık BaseMapper<Duyuru, DuyuruResponse> arayüzüne uyuyor
                             DosyaService dosyaServisi) {

        // BU SATIR ARTIK HATA VERMEYECEK:
        super(repository, mapper, Duyuru.class);
        this.dosyaServisi = dosyaServisi;
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        log.warn("Duyuru silme işlemi başlatıldı (UUID: {}). İlişkili fiziksel dosyalar da kalıcı olarak silinecek!", uuid);

        // 1. Duyuru entity'sini bul (durumu ne olursa olsun)
        Duyuru duyuru = repository.findByUuid(uuid)
                .orElseThrow(() -> notFound(uuid)); // BaseServiceImpl'den gelen 'notFound' yardımcısı

        // 2. Silinecek dosyaların UUID'lerini ve ilişki kayıtlarını topla
        // (Bu işlemi, ilişkiler silinmeden önce yapmalıyız)
        Set<UUID> silinecekDosyaUuidleri = duyuru.getDosyalar().stream()
                .map(dd -> dd.getDosya().getUuid())
                .collect(Collectors.toSet());

        // 3. ÖNCE: İlişki tablosu ('DuyuruDosyasi') kayıtlarını sil
        // Duyuru entity'sindeki 'orphanRemoval=true' ayarı sayesinde,
        // koleksiyonu temizleyip kaydettiğimizde, JPA bu ilişki kayıtlarını DB'den siler.
        // Bu, 'Dosya' entity'si silinmeden önce FK ihlali olmaması için kritiktir.
        if (!duyuru.getDosyalar().isEmpty()) {
            log.info("{} adet dosya ilişkisi 'DuyuruDosyasi' tablosundan siliniyor...", duyuru.getDosyalar().size());
            duyuru.getDosyalar().clear();
            repository.save(duyuru);
        }

        // 4. SONRA: Merkezi DosyaServisi'ni kullanarak dosyaları FİZİKSEL olarak sil
        // Bu metot, bizim "rockstar" mimarimiz gereği hem fiziksel dosyayı
        // hem de merkezi 'Dosya' entity kaydını siler.
        log.info("{} adet dosya, merkezi DosyaServisi kullanılarak kalıcı olarak silinecek...", silinecekDosyaUuidleri.size());
        for (UUID dosyaUuid : silinecekDosyaUuidleri) {
            try {
                dosyaServisi.sil(dosyaUuid); // <-- Merkezi silme işlemi
            } catch (Exception e) {
                // Eğer bir dosya silinemezse, @Transactional sayesinde tüm işlem
                // (ilişki silme ve duyuru soft-delete) geri alınacaktır.
                log.error("Dosya (UUID: {}) silinirken kritik bir hata oluştu! Tüm silme işlemi geri alınıyor.", dosyaUuid, e);
                // Hatayı yukarı fırlat ki Transaction geri alınsın
                throw new RuntimeException("Dosya silinemediği için duyuru silme işlemi iptal edildi.", e);
            }
        }
        log.info("{} adet dosya başarıyla sistemden kalıcı olarak silindi.", silinecekDosyaUuidleri.size());


        // 5. SON OLARAK: Duyuru'nun kendisini soft-delete yap
        // BaseServiceImpl'deki standart delete(entity) metodunu çağırabiliriz
        // veya manuel olarak yapabiliriz:
        beforeDelete(duyuru);
        duyuru.setDurum(Durum.SILINDI);
        repository.save(duyuru);
        afterDelete(duyuru);

        log.info("Duyuru (UUID: {}) başarıyla 'SILINDI' olarak işaretlendi. İşlem tamamlandı.", uuid);
    }

    @Override
    @Transactional
    public DuyuruResponse olustur(DuyuruCreateRequest request, MultipartFile[] dosyalar) {
        // 1. Gelen request DTO'sunu ana entity'ye çevir
        Duyuru duyuru = ((DuyuruMapper) this.mapper).createRequestToEntity(request);

        // 2. Yeni dosyaları işle
        if (dosyalar != null && dosyalar.length > 0) {
            duyuru.setDosyalar(new HashSet<>());
            for (MultipartFile dosya : dosyalar) {
                // 3. Merkezi servisi kullanarak dosyayı kaydet
                Dosya kaydedilenDosya = dosyaServisi.kaydet(dosya, "duyurular");

                // 4. İlişki tablosu (DuyuruDosyasi) entity'sini oluştur
                DuyuruDosyasi duyuruDosyasi = DuyuruDosyasi.builder()
                        .duyuru(duyuru)
                        .dosya(kaydedilenDosya)
                        .build();

                // 5. İlişkiyi ana entity'ye ekle
                duyuru.getDosyalar().add(duyuruDosyasi);
            }
        }

        // 6. Base hook'ları ve kaydetme işlemini yap
        beforeCreate(duyuru);
        duyuru = repository.save(duyuru);
        afterCreate(duyuru);

        // 7. Sonucu Response DTO'suna çevirip dön
        // ESKİ HATALI KOD: return ((DuyuruMapper) this.mapper).entityToResponse(duyuru);
        // YENİ DOĞRU KOD:
        return this.mapper.toDto(duyuru);
    }

    @Override
    @Transactional
    public DuyuruResponse guncelle(UUID uuid, DuyuruUpdateRequest request, MultipartFile[] yeniDosyalar) {
        // 1. Mevcut entity'yi bul
        Duyuru duyuru = getEntityAndDurum(uuid, Durum.AKTIF);

        // 2. Temel alanları (baslik, icerik vb.) DTO'dan entity'ye güncelle
        ((DuyuruMapper) this.mapper).updateRequestToEntity(request, duyuru);

        // 3. Silinmesi istenen dosyaları işle
        if (request.getSilinecekDosyaUuidleri() != null && !request.getSilinecekDosyaUuidleri().isEmpty()) {

            // Silinecek Dosya UUID'lerini al
            Set<UUID> silinecekDosyaUuidleri = request.getSilinecekDosyaUuidleri();

            // Duyuruya bağlı olan DuyuruDosyasi'larından silinecekleri bul
            Set<DuyuruDosyasi> silinecekIliskiler = duyuru.getDosyalar().stream()
                    .filter(dd -> silinecekDosyaUuidleri.contains(dd.getDosya().getUuid()))
                    .collect(Collectors.toSet());

            log.info("{} adet dosya ilişkisi duyurudan kaldırılıyor...", silinecekIliskiler.size());

            // İlişkileri kaldır. orphanRemoval=true sayesinde DuyuruDosyasi kayıtları DB'den silinecek.
            duyuru.getDosyalar().removeAll(silinecekIliskiler);

            // ÖNEMLİ NOT: Bu tasarımda, dosya sadece duyurudan kaldırılır.
            // Merkezi 'Dosya' kaydı ve fiziksel dosya SİLİNMEZ.
            // Eğer dosyanın tamamen silinmesi isteniyorsa, burada ek olarak:
            // silinecekIliskiler.forEach(dd -> dosyaServisi.sil(dd.getDosya().getUuid()));
            // çağrılmalıdır. (Ancak bu, FK ihlallerine karşı dikkatle yönetilmelidir)
        }

        // 4. Yeni eklenen dosyaları işle (oluşturma ile aynı mantık)
        if (yeniDosyalar != null && yeniDosyalar.length > 0) {
            for (MultipartFile dosya : yeniDosyalar) {
                Dosya kaydedilenDosya = dosyaServisi.kaydet(dosya, "duyurular");
                DuyuruDosyasi duyuruDosyasi = DuyuruDosyasi.builder()
                        .duyuru(duyuru)
                        .dosya(kaydedilenDosya)
                        .build();
                duyuru.getDosyalar().add(duyuruDosyasi);
            }
        }

        // 5. Base hook'ları ve kaydetme işlemini yap
        beforeUpdate(duyuru);
        duyuru = repository.save(duyuru);
        afterUpdate(duyuru);

        // 6. Sonucu Response DTO'suna çevirip dön
        return this.mapper.toDto(duyuru);
    }

    // --- BaseService'deki Uyumsuz Metotları Ezme ---
    // Bu metotlar artık kullanılmamalı, çünkü GİRDİ olarak Response DTO'su alıyorlar.
    @Override
    public DuyuruResponse create(DuyuruResponse dto) {
        throw new UnsupportedOperationException("Lütfen 'olustur(DuyuruCreateRequest, ...)' metodunu kullanın.");
    }

    @Override
    public DuyuruResponse update(Long id, DuyuruResponse dto) {
        throw new UnsupportedOperationException("Lütfen 'guncelle(UUID, DuyuruUpdateRequest, ...)' metodunu kullanın.");
    }

    @Override
    public DuyuruResponse update(UUID uuid, DuyuruResponse dto) {
        throw new UnsupportedOperationException("Lütfen 'guncelle(UUID, DuyuruUpdateRequest, ...)' metodunu kullanın.");
    }

    @Override
    public void delete(Long aLong) {
        throw new UnsupportedOperationException("Lütfen 'delete(UUID uuid, ...)' metodunu kullanın.");
    }

}