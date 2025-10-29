package com.gtombul.siteyonetimi.model.enums;

public enum ParaBirimi {

    TRY(1, "TRY", "Turk Lirasi"),
    USD(2, "USD", "Amerikan Dolari"),
    EUR(3, "EUR", "Euro");

    private final int id;
    private final String value;
    private final String label;

    ParaBirimi(int id, String value, String label) {
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


