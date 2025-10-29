package com.gtombul.siteyonetimi.service.bildirim;

import com.gtombul.siteyonetimi.service.bildirim.saglayici.SmsSaglayici;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TwilioSmsSaglayici implements SmsSaglayici {

    @Override
    public String gonder(String telefon, String icerik) {
        // Twilio SDK ya da REST ile gönderim
        log.info("Twilio SMS gonderildi -> {} : {}", telefon, icerik);
        return "OK";
    }
    
}
