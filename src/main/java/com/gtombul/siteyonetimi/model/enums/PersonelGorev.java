package com.gtombul.siteyonetimi.model.enums;

public enum PersonelGorev {
    GUVENLIK(1, "GUVENLIK", "Guvenlik"),
    TEMIZLIK(2, "TEMIZLIK", "Temizlik"),
    TEKNIK(3, "TEKNIK", "Teknik"),
    IDARI(4, "IDARI", "Idari"),
    MUHASEBE(5, "MUHASEBE", "Muhasebe"),
    YONETICI(6, "YONETICI", "Yonetici");

    private final int id;
    private final String value;
    private final String label;

    PersonelGorev(int id, String value, String label) {
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