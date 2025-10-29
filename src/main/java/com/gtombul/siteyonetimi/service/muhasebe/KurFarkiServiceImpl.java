package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.FisOlusturIstekDto;
import com.gtombul.siteyonetimi.dto.muhasebe.KurFarkiRequestDto;
import com.gtombul.siteyonetimi.dto.muhasebe.MuhasebeKalemDto;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.HareketTuru;
import com.gtombul.siteyonetimi.model.enums.HesapTipi;
import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import com.gtombul.siteyonetimi.model.muhasebe.Hesap;
import com.gtombul.siteyonetimi.repository.muhasebe.HesapRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeFisRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeKalemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KurFarkiServiceImpl implements KurFarkiService {

    private final HesapRepository hesapRepo;
    private final MuhasebeKalemRepository kalemRepo;
    private final MuhasebeFisRepository fisRepo;
    private final DovizKuruService kurServisi;
    private final MuhasebeService muhasebeServisi;

    @Override
    @Transactional
    public void kurFarkiOlustur(KurFarkiRequestDto istek) {
        LocalDate t = istek.getTarih();

        List<Hesap> hedefler = (istek.getHesapIdListe() != null && !istek.getHesapIdListe().isEmpty())
                ? hesapRepo.findAllById(istek.getHesapIdListe())
                : hesapRepo.findAll().stream()
                .filter(h -> (h.getHesapTipi() == HesapTipi.KASA || h.getHesapTipi() == HesapTipi.BANKA)
                        && h.getParaBirimi() != ParaBirimi.TRY
                        && h.getDurum() != Durum.SILINDI)
                .toList();

        // Kur farkı için gelir/gider hesaplarını hazırla (TRY)
        Long gelirId = hesapRepo.findByHesapTipiAndReferansIdAndDurumNot(HesapTipi.GELIR, null, Durum.SILINDI)
                .orElseGet(() -> hesapRepo.save(Hesap.builder().ad("Kur Farkı Geliri")
                        .hesapTipi(HesapTipi.GELIR).paraBirimi(ParaBirimi.TRY).build()))
                .getId();
        Long giderId = hesapRepo.findByHesapTipiAndReferansIdAndDurumNot(HesapTipi.GELIR, -1L, Durum.SILINDI) // -1L: ayırıcı
                .orElseGet(() -> hesapRepo.save(Hesap.builder().ad("Kur Farkı Gideri")
                        .hesapTipi(HesapTipi.GELIR).paraBirimi(ParaBirimi.TRY).referansId(-1L).build()))
                .getId();

        for (Hesap h : hedefler) {
            String fisNo = "KRF-" + h.getId() + "-" + t;
            if (fisRepo.findByFisNoAndDurumNot(fisNo, Durum.SILINDI).isPresent()) continue; // idempotent

            BigDecimal units = kalemRepo.dovizBakiye(h.getId());                  // döviz birim bakiyesi
            if (units == null || units.signum() == 0) continue;

            BigDecimal kur = kurServisi.kurAl(h.getParaBirimi(), ParaBirimi.TRY, t);
            BigDecimal tryDefter = kalemRepo.bakiye(h.getId());                    // TRY bazlı defter
            BigDecimal tryGercek = units.multiply(kur);
            BigDecimal delta = tryGercek.subtract(tryDefter).setScale(2, RoundingMode.HALF_UP);
            if (delta.signum() == 0) continue;

            // Delta > 0: varlık arttı → Hesap BORÇ, Gelir ALACAK
            var k1 = new MuhasebeKalemDto();
            var k2 = new MuhasebeKalemDto();
            if (delta.signum() > 0) {
                k1.setHesapId(h.getId());
                k1.setBorcTutar(delta);
                k2.setHesapId(gelirId);
                k2.setAlacakTutar(delta);
            } else {
                BigDecimal abs = delta.abs();
                k1.setHesapId(giderId);
                k1.setBorcTutar(abs);
                k2.setHesapId(h.getId());
                k2.setAlacakTutar(abs);
            }

            var fisIstek = new FisOlusturIstekDto();
            fisIstek.setFisNo(fisNo);
            fisIstek.setTarih(t);
            fisIstek.setHareketTuru(HareketTuru.KUR_FARKI);
            fisIstek.setAciklama(istek.getAciklama() == null ? "Kur değerleme" : istek.getAciklama());
            fisIstek.setKalemler(List.of(k1, k2));

            muhasebeServisi.fisOlustur(fisIstek);
        }
    }

}