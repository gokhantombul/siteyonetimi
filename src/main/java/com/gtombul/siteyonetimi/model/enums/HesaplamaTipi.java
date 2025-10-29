package com.gtombul.siteyonetimi.model.enums;

public enum HesaplamaTipi {

    SABIT(1, "SABIT", "Sabit"),
    M2(2, "M2", "M2"),
    KATSAYI(3, "KATSAYI", "Kat Sayi");

    private final int id;
    private final String value;
    private final String label;

    HesaplamaTipi(int id, String value, String label) {
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