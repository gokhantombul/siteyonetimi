package com.gtombul.siteyonetimi.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZiyaretciDurum {

    DAVETLI(1, "DAVETLI", "Davetli"), // henüz gelmemiş (daire null olabilir)
    GELDI(2, "GELDI", "Geldi"), // daireye giriş yaptı (daire zorunlu)
    CIKTI(3, "CIKTI", "Çıktı"); // siteden ayrıldı (daire null olabilir)

    private final int id;
    private final String value;
    private final String label;

    ZiyaretciDurum(int id, String value, String label) {
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
