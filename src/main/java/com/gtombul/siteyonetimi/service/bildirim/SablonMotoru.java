package com.gtombul.siteyonetimi.service.bildirim;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SablonMotoru {

    public String derle(String sablon, Map<String,String> degiskenler) {
        if (sablon == null) return null;
        String sonuc = sablon;
        if (degiskenler != null) {
            for (var e : degiskenler.entrySet()) {
                sonuc = sonuc.replace("{"+e.getKey()+"}", e.getValue());
            }
        }
        return sonuc;
    }
}
