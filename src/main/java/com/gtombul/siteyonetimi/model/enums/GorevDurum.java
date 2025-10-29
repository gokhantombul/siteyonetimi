package com.gtombul.siteyonetimi.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GorevDurum {
    ATANDI(1, "ATANDI", "Atandı"),
    DEVAM_EDIYOR(2, "DEVAM_EDIYOR", "Devam Ediyor"),
    TAMAMLANDI(3, "TAMAMLANDI", "Tamamlandı"),
    YENIDEN_ATANDI(4, "YENIDEN_ATANDI", "Yeniden Atandı"),
    IPTAL_EDILDI(5, "IPTAL_EDILDI", "İptal Edildi");

    private final int id;
    private final String value;
    private final String label;

    GorevDurum(int id, String value, String label) {
        this.id = id;
        this.value = value;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

}