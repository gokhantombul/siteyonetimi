package com.gtombul.siteyonetimi.model.ilan;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IlanOnayDurumu {

    TASLAK(1, "TASLAK", "Taslak"),
    INCELEME(2, "INCELEME", "İnceleme"),
    ONAYLI(3, "ONAYLI", "Onaylı"),
    REDDEDILDI(4, "REDDEDILDI", "Reddedildiq");

    private final int id;
    private final String value;
    private final String label;

    IlanOnayDurumu(int id, String value, String label) {
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
