package com.gtombul.siteyonetimi.model.ilan;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IlanDurum {

    TASLAK(1, "TASLAK", "Taslak"),
    AKTIF(2, "AKTIF", "Aktif"),
    PASIF(3, "PASIF", "Pasif"),
    SATILDI(4, "SATILDI", "Satıldı"),
    SURESI_DOLDU(5, "SURESI_DOLDU", "Süresi Doldu");

    private final int id;
    private final String value;
    private final String label;

    IlanDurum(int id, String value, String label) {
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
