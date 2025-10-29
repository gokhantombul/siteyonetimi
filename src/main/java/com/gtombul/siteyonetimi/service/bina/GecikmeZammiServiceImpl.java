package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.muhasebe.FisOlusturIstekDto;
import com.gtombul.siteyonetimi.dto.muhasebe.MuhasebeKalemDto;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.HareketTuru;
import com.gtombul.siteyonetimi.model.enums.HesapTipi;
import com.gtombul.siteyonetimi.model.muhasebe.Hesap;
import com.gtombul.siteyonetimi.repository.muhasebe.AidatAtamaKaydiRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.HesapRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeFisRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeKalemRepository;
import com.gtombul.siteyonetimi.service.muhasebe.HesapService;
import com.gtombul.siteyonetimi.service.muhasebe.MuhasebeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class GecikmeZammiServiceImpl implements GecikmeZammiService {

    private static final ZoneId IST = ZoneId.of("Europe/Istanbul");
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final HesapRepository hesapRepo;
    private final AidatAtamaKaydiRepository atamaRepo;
    private final MuhasebeKalemRepository kalemRepo;
    private final MuhasebeFisRepository fisRepo;

    private final HesapService hesapServisi;       // GELIR hesabı yoksa oluşturmak için
    private final MuhasebeService muhasebeServisi; // fiş kesmek için

    @Override
    @Transactional
    public void bugunIcinUygula() {
        LocalDate today = LocalDate.now(IST);
        YearMonth donem = YearMonth.from(today);

        // Her daire hesabı için kontrol et
        var daireler = hesapRepo.findAllByHesapTipiAndDurumNot(HesapTipi.DAIRE, Durum.SILINDI);
        for (Hesap daire : daireler) {
            // Bu ay için atama varsa (aidat yazılmışsa) ilgili planı bul
            var atamaOpt = atamaRepo.findByDaireHesapIdAndDonemYyyyMmAndDurumNot(daire.getId(), donem.toString(), Durum.SILINDI);
            if (atamaOpt.isEmpty()) continue;

            var plan = atamaOpt.get().getPlan();
            if (plan.getGecikmeOraniGunluk() == null || plan.getGecikmeOraniGunluk().signum() <= 0) continue;

            // Vade
            int gun = Math.min(plan.getVadeGunu(), donem.lengthOfMonth());
            LocalDate vadeTarihi = donem.atDay(gun);
            if (!today.isAfter(vadeTarihi)) continue; // vade gelmemiş

            // Idempotent: aynı gün için aynı daireye GZM fişi kesildiyse atla
            String fisNo = "GZM-" + daire.getId() + "-" + today.format(FMT);
            if (fisRepo.findByFisNoAndDurumNot(fisNo, Durum.SILINDI).isPresent()) continue;

            // Güncel bakiye (yalnız aktif kalem/fişler)
            BigDecimal bakiye = kalemRepo.bakiye(daire.getId());
            if (bakiye.signum() <= 0) continue;

            // 1 günlük gecikme zammı (basit doğrusal)
            BigDecimal oran = plan.getGecikmeOraniGunluk();
            BigDecimal ceza = bakiye.multiply(oran).setScale(2, RoundingMode.HALF_UP);
            if (ceza.signum() <= 0) continue;

            // "Gecikme Zammı Geliri" hesabı (GELIR, referansId=null) yoksa oluştur
            var gelirHesapDto = hesapServisi.hesapGetirVeyaOlustur(
                    HesapTipi.GELIR, null, "Gecikme Zammı Geliri");

            // Fişi kes (Daire BORÇ, Gelir ALACAK)
            var istek = new FisOlusturIstekDto();
            istek.setFisNo(fisNo);
            istek.setTarih(today);
            istek.setHareketTuru(HareketTuru.GECIKME_ZAMMI);
            istek.setAciklama("Gecikme zammı (" + donem + ")");
            istek.setKalemler(java.util.List.of(
                    MuhasebeKalemDto.builder().hesapId(daire.getId()).borcTutar(ceza).build(),
                    MuhasebeKalemDto.builder().hesapId(gelirHesapDto.getId()).alacakTutar(ceza).build()
            ));

            muhasebeServisi.fisOlustur(istek);
        }
    }

}
