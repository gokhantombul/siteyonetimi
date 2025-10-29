package com.gtombul.siteyonetimi.service.bildirim.saglayici;

public interface SmsSaglayici {

    String gonder(String telefon, String icerik) throws Exception;

}
