package com.gtombul.siteyonetimi.service.bildirim;

import com.gtombul.siteyonetimi.service.bildirim.saglayici.PushSaglayici;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmPushSaglayici implements PushSaglayici {

    //private final WebClient fcmClient; // Bearer token ile config

    @Override
    public String gonder(String token, String baslik, String icerik, boolean html) throws Exception {
        var payload = """
                {
                  "message": {
                    "token": "%s",
                    "notification": { "title": "%s", "body": "%s" }
                  }
                }
                """.formatted(token, baslik, icerik);

        /*var resp = fcmClient.post()
                .uri("/v1/projects/{projectId}/messages:send", "YOUR_PROJECT_ID")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("FCM push sonucu: {}", resp);*/
        return null;
    }

}

