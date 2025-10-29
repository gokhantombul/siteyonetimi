package com.gtombul.siteyonetimi.service.bildirim.saglayici;

public interface EmailSaglayici {

    String gonder(String eposta, String konu, String icerik, boolean html) throws Exception;

}

