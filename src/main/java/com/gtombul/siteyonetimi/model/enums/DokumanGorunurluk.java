package com.gtombul.siteyonetimi.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DokumanGorunurluk {

    SITE_YONETIMI(1, "SITE_YONETIMI", "Site Yönetimi"),
    PERSONEL(2, "PERSONEL", "Personel"),
    SAKIN(3, "SAKIN", "Sakin"),
    SADECE_YONETICI(4, "SADECE_YONETICI", "Sadece Yönetici");

    private final int id;
    private final String value;
    private final String label;

    DokumanGorunurluk(int id, String value, String label) {
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
