package com.gtombul.siteyonetimi.model.enums;

public enum HareketTuru {

    AIDAT(1, "AIDAT", "Aidat"),
    TAHSILAT(2, "TAHSILAT", "Tahsilat"),
    ODEME(3, "ODEME", "Odeme"),
    CEZA(4, "CEZA", "Ceza"),
    GECIKME_ZAMMI(5, "GECIKME_ZAMMI", "Gecikme Zammi"),
    DEMIRBAS(6, "DEMIRBAS", "Demirbas"),
    MAHSUP(7, "MAHSUP", "Mahsup"),
    VIRMAN(8, "VIRMAN", "Virman"),
    KUR_FARKI(9, "KUR_FARKI", "Kur Farki"),
    DIGER(10, "DIGER", "Diger");

    private final int id;
    private final String value;
    private final String label;

    HareketTuru(int id, String value, String label) {
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
