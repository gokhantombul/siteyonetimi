package com.gtombul.siteyonetimi.model.enums;

public enum FirmaTuru {
    SAHIP(1, "SAHIP", "Sahip"),          // Site sahibi
    YUKLENICI(2, "YUKLENICI", "Yüklenici"),      // İnşaat yapan firma
    TEDARIKCI(3, "TEDARIKCI", "Tedarikçi"),      // Malzeme/hizmet sağlayıcı
    BAKIM(4, "BAKIM", "Bakım"),          // Asansör, bina bakım firmaları
    GUVENLIK(5, "GUVENLIK", "Güvenlik"),       // Özel güvenlik hizmetleri
    DIGER(6, "DIGER", "Diğer");

    private final int id;
    private final String value;
    private final String label;

    FirmaTuru(int id, String value, String label) {
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
