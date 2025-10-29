package com.gtombul.siteyonetimi.model.kargo;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum KargoDurum {

    BEKLIYOR(1, "BEKLIYOR", "Bekliyor"), // Siteye giriş yaptı, alıcıdan bekliyor
    TESLIM_EDILDI(2, "TESLIM_EDILDI", "Teslim Edildi"), // Daire sakinine teslim edildi
    IADE_EDILDI(3, "IADE_EDILDI", "İade Edildi"), // Kargo firmasına iade edildi
    KAYIP(4, "KAYIP", "Kayıp"); // Kayıp/hasarlı süreç

    private final int id;
    private final String value;
    private final String label;

    KargoDurum(int id, String value, String label) {
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
