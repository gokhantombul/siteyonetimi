package com.gtombul.siteyonetimi.service.bildirim;

import com.gtombul.siteyonetimi.service.bildirim.saglayici.SmsSaglayici;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NetgsmSmsSaglayici implements SmsSaglayici {

    // private final WebClient netgsmClient; // Bean: baseUrl, auth ekle

    @Override
    public String gonder(String telefon, String icerik) throws Exception {
        // NetGSM API formatına uygun body ile çağrı
        /*var resp = netgsmClient.post()
                .uri("/sms/send")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SmsIstek(telefon, icerik))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("NetGSM SMS gönderim sonucu: {}", resp);*/
        return null;
    }

    private record SmsIstek(String msisdn, String message) {
    }

}