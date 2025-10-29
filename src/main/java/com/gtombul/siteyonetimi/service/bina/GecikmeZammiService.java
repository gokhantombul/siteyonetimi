package com.gtombul.siteyonetimi.service.bina;

public interface GecikmeZammiService {

    /** Bugün için vadesi geçmiş dairelere 1 günlük gecikme zammı uygular (idempotent). */
    void bugunIcinUygula();

}
