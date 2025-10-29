package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.FisOlusturIstekDto;
import com.gtombul.siteyonetimi.dto.muhasebe.MuhasebeFisDto;
import com.gtombul.siteyonetimi.dto.muhasebe.MuhasebeKalemDto;
import com.gtombul.siteyonetimi.mapper.muhasebe.MuhasebeFisMapper;
import com.gtombul.siteyonetimi.model.enums.HareketTuru;
import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import com.gtombul.siteyonetimi.model.muhasebe.MuhasebeFis;
import com.gtombul.siteyonetimi.model.muhasebe.MuhasebeKalem;
import com.gtombul.siteyonetimi.repository.muhasebe.HesapRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeFisRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeKalemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MuhasebeServiceImpl implements MuhasebeService {

    private final MuhasebeFisRepository fisRepository;
    private final MuhasebeKalemRepository kalemRepository;
    private final MuhasebeFisMapper fisMapper;
    private final HesapRepository hesapRepository;
    private final DovizKuruService dovizKuruService;

    @Override
    @Transactional
    public MuhasebeFisDto fisOlustur(FisOlusturIstekDto istek) {
        // 1) kalem validasyon: XOR ve toplam borç == toplam alacak
        BigDecimal toplamBorc = BigDecimal.ZERO;
        BigDecimal toplamAlacak = BigDecimal.ZERO;

        for (var k : istek.getKalemler()) {
            if ((k.getBorcTutar() == null) == (k.getAlacakTutar() == null)) {
                throw new IllegalArgumentException("Her kalemde borç XOR alacak dolu olmalı.");
            }
            if (k.getBorcTutar() != null) toplamBorc = toplamBorc.add(k.getBorcTutar());
            if (k.getAlacakTutar() != null) toplamAlacak = toplamAlacak.add(k.getAlacakTutar());
        }
        if (toplamBorc.setScale(2, RoundingMode.HALF_UP)
                .compareTo(toplamAlacak.setScale(2, RoundingMode.HALF_UP)) != 0) {
            throw new IllegalStateException("Fişte borç ve alacak toplamları eşit olmalı.");
        }

        // 2) fiş başlığı
        if (fisRepository.findByFisNo(istek.getFisNo()).isPresent()) {
            throw new IllegalStateException("Bu fisNo daha önce kullanılmış: " + istek.getFisNo());
        }

        var fis = MuhasebeFis.builder()
                .fisNo(istek.getFisNo())
                .tarih(istek.getTarih())
                .hareketTuru(istek.getHareketTuru())
                .aciklama(istek.getAciklama())
                .build();
        fis = fisRepository.save(fis);

        // 3) kalemler
        for (var kdto : istek.getKalemler()) {
            var hesap = hesapRepository.findById(kdto.getHesapId())
                    .orElseThrow(() -> new IllegalArgumentException("Hesap bulunamadı: " + kdto.getHesapId()));

            BigDecimal borcTry = kdto.getBorcTutar();
            BigDecimal alacakTry = kdto.getAlacakTutar();
            BigDecimal dovizTutar = kdto.getDovizTutar();
            var dovizCinsi = hesap.getParaBirimi(); // hesap para birimi

            MuhasebeKalem kalem;
            if (dovizCinsi != ParaBirimi.TRY) {
                if (dovizTutar == null)
                    throw new IllegalArgumentException("Döviz hesaplarında dovizTutar zorunludur.");
                BigDecimal kur = (kdto.getKur() != null) ? kdto.getKur() : dovizKuruService.kurAl(dovizCinsi, ParaBirimi.TRY, istek.getTarih());

                // TRY tutarları kur ile üret (XOR olduğundan biri null)
                if (borcTry != null) borcTry = dovizTutar.multiply(kur);
                if (alacakTry != null) alacakTry = dovizTutar.multiply(kur);

                kalem = MuhasebeKalem.builder()
                        .fis(fis).hesap(hesap)
                        .borcTutar(borcTry == null ? null : borcTry.setScale(2, RoundingMode.HALF_UP))
                        .alacakTutar(alacakTry == null ? null : alacakTry.setScale(2, RoundingMode.HALF_UP))
                        .dovizCinsi(dovizCinsi.name())
                        .dovizTutar(dovizTutar.setScale(6, RoundingMode.HALF_UP))
                        .kur(kur.setScale(6, RoundingMode.HALF_UP))
                        .aciklama(kdto.getAciklama())
                        .build();
            } else {
                kalem = MuhasebeKalem.builder()
                        .fis(fis)
                        .hesap(hesap)
                        .borcTutar(kdto.getBorcTutar() == null ? null : kdto.getBorcTutar().setScale(2, RoundingMode.HALF_UP))
                        .alacakTutar(kdto.getAlacakTutar() == null ? null : kdto.getAlacakTutar().setScale(2, RoundingMode.HALF_UP))
                        .aciklama(kdto.getAciklama())
                        .build();
            }
            kalemRepository.save(kalem);
        }

        // 4) dönüş
        var dto = fisMapper.toDto(fis);
        dto.setKalemler(istek.getKalemler());
        return dto;
    }

    @Override
    @Transactional
    public MuhasebeFisDto tahsilatGir(Long daireHesapId, Long kasaVeyaBankaHesapId, BigDecimal tutar, String aciklama) {
        var istek = new FisOlusturIstekDto();
        istek.setFisNo("THS-" + System.currentTimeMillis());
        istek.setTarih(LocalDate.now());
        istek.setHareketTuru(HareketTuru.TAHSILAT);
        istek.setAciklama(aciklama);

        var k1 = new MuhasebeKalemDto(); // Kasa/Banka BORÇ
        k1.setHesapId(kasaVeyaBankaHesapId);
        k1.setBorcTutar(tutar);
        var k2 = new MuhasebeKalemDto(); // Daire ALACAK
        k2.setHesapId(daireHesapId);
        k2.setAlacakTutar(tutar);

        istek.setKalemler(List.of(k1, k2));
        return fisOlustur(istek);
    }

}