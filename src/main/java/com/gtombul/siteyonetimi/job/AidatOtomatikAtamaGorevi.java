package com.gtombul.siteyonetimi.job;

import com.gtombul.siteyonetimi.service.muhasebe.AidatPlaniService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class AidatOtomatikAtamaGorevi {

    private final AidatPlaniService aidatPlaniService;

    // Her ayın 1'i 03:00, Europe/Istanbul
    @Scheduled(cron = "0 0 3 1 * *", zone = "Europe/Istanbul")
    public void calistir() {
        YearMonth donem = YearMonth.now(ZoneId.of("Europe/Istanbul"));
        aidatPlaniService.tumPlanlarIcinAidatOlustur(donem);
    }

}