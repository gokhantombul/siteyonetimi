package com.gtombul.siteyonetimi.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TalepOncelik {
    DUSUK(1, "DUSUK", "Düşük"),
    ORTA(2, "ORTA", "Orta"),
    YUKSEK(3, "YUKSEK", "Yüksek"),
    ACIL(4, "ACIL", "Acil");

    private final int id;
    private final String value;
    private final String label;

    TalepOncelik(int id, String value, String label) {
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