package com.gtombul.siteyonetimi.model.enums;

public enum OturumTipi {

    MULK_SAHIBI(1, "MULK_SAHIBI", "Mulk Sahibi"),
    KIRACI(2, "KIRACI", "Kiraci"),
    MISAFIR(3, "MISAFIR", "Misafir");

    private final int id;
    private final String value;
    private final String label;

    OturumTipi(int id, String value, String label) {
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