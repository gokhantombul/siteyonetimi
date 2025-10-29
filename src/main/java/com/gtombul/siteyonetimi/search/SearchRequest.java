package com.gtombul.siteyonetimi.search;

import java.util.List;

public record SearchRequest(
        String keyword,           // null ise anahtar kelime araması yapılmaz
        List<SearchFilter> filters,
        Integer page,
        Integer size,
        String sort               // ör: "ad,asc" veya "createdAt,desc"
) {
}
