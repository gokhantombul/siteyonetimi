package com.gtombul.siteyonetimi.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SozlesmeTipi {

    BAKIM(1, "BAKIM", "Bakım"),
    HIZMET(2, "HIZMET", "Hizmet"),
    KIRALAMA(3, "KIRALAMA", "Kiralama"),
    TEDARIK(4, "TEDARIK", "Tedarik"),
    DIGER(5, "DIGER", "Diğer");

    private final int id;
    private final String value;
    private final String label;

    SozlesmeTipi(int id, String value, String label) {
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
