package com.gtombul.siteyonetimi.model.anket;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum KararOyu {

    EVET(1, "EVET", "Evet"),
    HAYIR(2, "HAYIR", "Hayır"),
    CEKIMSER(3, "CEKIMSER", "Çekimser");

    private final int id;
    private final String value;
    private final String label;

    KararOyu(int id, String value, String label) {
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
