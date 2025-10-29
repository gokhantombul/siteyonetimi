package com.gtombul.siteyonetimi.model.enums;

public enum DaireTipi {
    KONUT(1, "KONUT", "Konut"),
    TICARI(2, "TICARI", "Ticari"),
    OFIS(3, "OFIS", "Ofis"),
    DUKKAN(4, "DUKKAN", "Dukkan");

    private final int id;
    private final String value;
    private final String label;

    DaireTipi(int id, String value, String label) {
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