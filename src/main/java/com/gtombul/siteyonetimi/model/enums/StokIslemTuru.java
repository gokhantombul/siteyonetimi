package com.gtombul.siteyonetimi.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StokIslemTuru {
    GIRIS(1, "GIRIS", "Giriş"), // Depoya giriş
    CIKIS(2, "CIKIS", "Çıkış"), // Tüketim / düşüm
    SAYIM(3, "SAYIM", "Sayım"), // Envanter sayımı farkları
    ZIMMET(4, "ZIMMET", "Zimmet"); // (Opsiyonel) kişi/daire zimmeti

    private final int id;
    private final String value;
    private final String label;

    StokIslemTuru(int id, String value, String label) {
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
