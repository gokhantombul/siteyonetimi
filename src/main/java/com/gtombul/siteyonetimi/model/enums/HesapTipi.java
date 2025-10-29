package com.gtombul.siteyonetimi.model.enums;

public enum HesapTipi {

    SITE(1, "SITE", "Site"),
    BLOK(2, "BLOK", "Blok"),
    DAIRE(3, "DAIRE", "Daire"),
    KASA(4, "KASA", "Kasa"),
    BANKA(5, "BANKA", "Banka"),
    TEDARIKCI(6, "TEDARIKCI", "Tedarikci"),
    PERSONEL(7, "PERSONEL", "Personel"),
    GELIR(8, "GELIR", "Gelir");

    private final int id;
    private final String value;
    private final String label;

    HesapTipi(int id, String value, String label) {
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