package com.gtombul.siteyonetimi.job;

import com.gtombul.siteyonetimi.service.bina.GecikmeZammiService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GecikmeZammiGorevi {

    private final GecikmeZammiService gecikmeZammiService;

    /**
     * Her gün 02:30, Europe/Istanbul
     */
    @Scheduled(cron = "0 30 2 * * *", zone = "Europe/Istanbul")
    public void calistir() {
        gecikmeZammiService.bugunIcinUygula();
    }

}
