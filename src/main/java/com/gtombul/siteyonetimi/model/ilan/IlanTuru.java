package com.gtombul.siteyonetimi.model.ilan;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IlanTuru {

    ESYA(1, "ESYA", "Eşya"),
    HIZMET(2, "HIZMET", "Hizmet"),
    ARAC(3, "ARAC", "Araç"),
    DIGER(4, "DIGER", "Diğer");

    private final int id;
    private final String value;
    private final String label;

    IlanTuru(int id, String value, String label) {
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