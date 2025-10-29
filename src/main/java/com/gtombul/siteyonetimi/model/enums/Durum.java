package com.gtombul.siteyonetimi.model.enums;

public enum Durum {

    AKTIF(1, "AKTIF", "Aktif"),
    PASIF(2, "PASIF", "Pasif"),
    SILINDI(3, "SILINDI", "Silindi");

    private final int id;
    private final String value;
    private final String label;

    Durum(int id, String value, String label) {
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


