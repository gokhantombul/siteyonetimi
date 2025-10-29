package com.gtombul.siteyonetimi.model.anket;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AnketTuru {

    ANKET(1, "ANKET", "Anket"), // Çoktan seçmeli
    KARAR(2, "KARAR", "Karar"); // Evet/Hayır/Çekimser

    private final int id;
    private final String value;
    private final String label;

    AnketTuru(int id, String value, String label) {
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
