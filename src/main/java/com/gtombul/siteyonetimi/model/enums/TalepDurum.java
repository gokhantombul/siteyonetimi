package com.gtombul.siteyonetimi.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TalepDurum {
    ACIK(1, "ACIK", "Açık"),
    DEGERLENDIRILIYOR(2, "DEGERLENDIRILIYOR", "Değerlendiriliyor"),
    PLANLANDI(3, "PLANLANDI", "Planlandı"),
    ISLEMDE(4, "ISLEMDE", "İşlemde"),
    TAMAMLANDI(5, "TAMAMLANDI", "Tamamlandı"),
    IPTAL_EDILDI(6, "IPTAL_EDILDI", "İptal Edildi"),
    REDDEDILDI(7, "REDDEDILDI", "Reddedildi");

    private final int id;
    private final String value;
    private final String label;

    TalepDurum(int id, String value, String label) {
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