package com.gtombul.siteyonetimi.model.bildirim;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BildirimOncelik {

    DUSUK(1, "DUSUK", "Düşük"),
    NORMAL(2, "NORMAL", "Normal"),
    YUKSEK(3, "YUKSEK", "Yüksek");

    private final int id;
    private final String value;
    private final String label;

    BildirimOncelik(int id, String value, String label) {
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
