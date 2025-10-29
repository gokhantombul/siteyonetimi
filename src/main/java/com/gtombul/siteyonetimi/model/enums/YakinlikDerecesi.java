package com.gtombul.siteyonetimi.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum YakinlikDerecesi {
    AILE(1, "AILE", "Aile"),
    ARKADAS(2, "ARKADAS", "Arkadaş"),
    IS(3, "IS", "İş"),
    DIGER(4, "DIGER", "Diğer");

    private final int id;
    private final String value;
    private final String label;

    YakinlikDerecesi(int id, String value, String label) {
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
