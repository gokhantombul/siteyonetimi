package com.gtombul.siteyonetimi.service.anket;

import com.gtombul.siteyonetimi.dto.anket.*;
import com.gtombul.siteyonetimi.mapper.anket.AnketMapper;
import com.gtombul.siteyonetimi.model.anket.*;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import com.gtombul.siteyonetimi.repository.anket.AnketOyRepository;
import com.gtombul.siteyonetimi.repository.anket.AnketRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class AnketServiceImpl extends BaseServiceImpl<Anket, AnketDto, Long> implements AnketService {

    private final AnketRepository anketRepository;
    private final AnketOyRepository anketOyRepository;
    private final AnketMapper anketMapper;

    public AnketServiceImpl(AnketRepository anketRepository, AnketOyRepository anketOyRepository, AnketMapper anketMapper) {
        super(anketRepository, anketMapper, Anket.class);
        this.anketRepository = anketRepository;
        this.anketOyRepository = anketOyRepository;
        this.anketMapper = anketMapper;
    }

    @Override
    @Transactional
    public AnketDto olustur(AnketOlusturDto istek) {
        log.info("Anket/Karar oluşturma isteği alındı: {}", istek.getBaslik());

        if (anketRepository.existsByBaslikIgnoreCaseAndDurumNot(istek.getBaslik(), Durum.SILINDI)) {
            throw new IllegalArgumentException("Bu başlıkta aktif bir anket/karar zaten var.");
        }

        AnketTuru tur = AnketTuru.valueOf(istek.getTur().toUpperCase(Locale.ROOT));
        if (istek.getBaslangicTarihi().isAfter(istek.getBitisTarihi())) {
            throw new IllegalArgumentException("Başlangıç tarihi bitiş tarihinden sonra olamaz.");
        }

        Anket anket = Anket.builder()
                .baslik(istek.getBaslik().trim())
                .aciklama(Optional.ofNullable(istek.getAciklama()).map(String::trim).orElse(null))
                .tur(tur)
                .anonimMi(istek.isAnonimMi())
                .baslangicTarihi(istek.getBaslangicTarihi())
                .bitisTarihi(istek.getBitisTarihi())
                .build();

        if (tur == AnketTuru.ANKET) {
            if (istek.getSecenekMetinleri() == null || istek.getSecenekMetinleri().isEmpty()) {
                throw new IllegalArgumentException("ANKET türü için en az 1 seçenek zorunludur.");
            }
            int i = 1;
            for (String metin : istek.getSecenekMetinleri()) {
                AnketSecenek secenek = AnketSecenek.builder()
                        .anket(anket)
                        .metin(metin.trim())
                        .siraNo(i++)
                        .build();
                anket.getSecenekler().add(secenek);
            }
        }

        Anket kayit = anketRepository.save(anket);
        log.info("Anket/Karar oluşturuldu: id={}, baslik={}", kayit.getId(), kayit.getBaslik());
        return anketMapper.toDto(kayit);
    }

    @Override
    @Transactional
    public void oyKullan(OyKullanDto oyIstek) {
        log.info("Oy kullanma isteği: anketId={}, kisiId={}", oyIstek.getAnketId(), oyIstek.getKisiId());

        Anket anket = anketRepository.aktifBulById(oyIstek.getAnketId())
                .orElseThrow(() -> new EntityNotFoundException("Anket/Karar bulunamadı"));

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(anket.getBaslangicTarihi()) || now.isAfter(anket.getBitisTarihi())) {
            throw new IllegalStateException("Anket/Karar oy kullanmaya kapalı.");
        }

        if (anketOyRepository.existsByAnket_IdAndKisi_IdAndDurumNot(anket.getId(), oyIstek.getKisiId(), Durum.SILINDI)) {
            throw new IllegalStateException("Bu anket/karar için zaten oy kullanılmış.");
        }

        // Kisi referansı hafifletmek istersen sadece kisiId tutan gölge alan yaklaşımına gidebilirdik.
        Kisi kisiRef = Kisi.builder().id(oyIstek.getKisiId()).build(); // referans

        AnketOy oy = AnketOy.builder()
                .anket(anket)
                .kisi(kisiRef)
                .daireId(oyIstek.getDaireId())
                .build();

        if (anket.getTur() == AnketTuru.ANKET) {
            if (oyIstek.getSecenekId() == null) {
                throw new IllegalArgumentException("ANKET türünde secenekId zorunludur.");
            }
            // Entity yüklemeden sadece id bağlamak için:
            AnketSecenek secenekRef = AnketSecenek.builder().id(oyIstek.getSecenekId()).build();
            oy.setSecenek(secenekRef);
        } else {
            if (oyIstek.getKararOyu() == null) {
                throw new IllegalArgumentException("KARAR türünde kararOyu zorunludur.");
            }
            oy.setKararOyu(KararOyu.valueOf(oyIstek.getKararOyu().toUpperCase(Locale.ROOT)));
        }

        anketOyRepository.save(oy);
        log.info("Oy kaydedildi: anketId={}, kisiId={}", anket.getId(), oyIstek.getKisiId());
    }

    @Override
    @Transactional(readOnly = true)
    public AnketSonucDto sonucGetir(Long anketId) {
        Anket anket = anketRepository.aktifBulById(anketId)
                .orElseThrow(() -> new EntityNotFoundException("Anket/Karar bulunamadı"));

        long toplam = anketOyRepository.sayToplamOy(anket.getId());

        AnketSonucDto.AnketSonucDtoBuilder b = AnketSonucDto.builder()
                .anketId(anket.getId())
                .baslik(anket.getBaslik())
                .tur(anket.getTur().name())
                .toplamOy(toplam);

        if (anket.getTur() == AnketTuru.KARAR) {
            long evet = anketOyRepository.sayEvet(anket.getId());
            long hayir = anketOyRepository.sayHayir(anket.getId());
            long cekimser = anketOyRepository.sayCekimser(anket.getId());
            b.evetSayisi(evet).hayirSayisi(hayir).cekimserSayisi(cekimser);
        } else {
            Map<Long, Long> sayim = new HashMap<>();
            for (Object[] row : anketOyRepository.saySecenekBazli(anket.getId())) {
                Long secenekId = (Long) row[0];
                Long cnt = (Long) row[1];
                sayim.put(secenekId, cnt);
            }
            List<SecenekSonucDto> liste = anket.getSecenekler().stream()
                    .sorted(Comparator.comparing(AnketSecenek::getSiraNo))
                    .map(s -> {
                        long oy = sayim.getOrDefault(s.getId(), 0L);
                        double yuzde = toplam == 0 ? 0d : (oy * 100.0) / toplam;
                        return SecenekSonucDto.builder()
                                .secenekId(s.getId())
                                .secenekMetin(s.getMetin())
                                .oySayisi(oy)
                                .yuzde(yuzde)
                                .build();
                    })
                    .toList();
            b.secenekSonuclari(liste);
        }
        return b.build();
    }

}
