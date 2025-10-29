package com.gtombul.siteyonetimi.search;

public record SearchFilter(
        String field,       // alan adı (entity property)
        String operator,    // eq, ne, like, gt, lt, ge, le, in
        String value        // değer (virgüllü liste de olabilir)
) {}
