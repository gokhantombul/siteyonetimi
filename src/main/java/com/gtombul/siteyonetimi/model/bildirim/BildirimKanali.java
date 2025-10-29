package com.gtombul.siteyonetimi.model.bildirim;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BildirimKanali {

    SMS(1, "SMS", "Sms"),
    EMAIL(2, "EMAIL", "Email"),
    PUSH(3, "PUSH", "Push");

    private final int id;
    private final String value;
    private final String label;

    BildirimKanali(int id, String value, String label) {
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
