package com.gtombul.siteyonetimi.search;

import com.gtombul.siteyonetimi.annotation.Searchable;
import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.Durum;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public final class SpecificationBuilder {

    private SpecificationBuilder() {
    }

    /**
     * Anahtar kelimeyi @Searchable alanlarda case-insensitive LIKE ile arar.
     * Ayrıca filters listesini AND ile uygular. Silinmiş kayıtları (DELETED) otomatik eler.
     */
    public static <E extends BaseEntity> Specification<E> build(Class<E> entityClass,
                                                                String keyword,
                                                                List<SearchFilter> filters) {
        Specification<E> spec = notDeleted();

        if (keyword != null && !keyword.isBlank()) {
            spec = spec.and(keywordSpec(entityClass, keyword));
        }
        if (filters != null && !filters.isEmpty()) {
            for (SearchFilter f : filters) {
                spec = spec.and(filterSpec(f));
            }
        }
        return spec;
    }

    private static <E extends BaseEntity> Specification<E> notDeleted() {
        return (root, query, cb) -> cb.notEqual(root.get("durum"), Durum.SILINDI);
    }

    private static <E> Specification<E> keywordSpec(Class<E> entityClass, String keyword) {
        final String likeValue = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        final List<String> searchableFields = Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Searchable.class))
                .map(Field::getName)
                .toList();

        return (root, query, cb) -> {
            if (searchableFields.isEmpty()) return cb.conjunction();
            List<Predicate> ors = new ArrayList<>();
            for (String field : searchableFields) {
                Expression<String> expr = cb.lower(root.get(field).as(String.class));
                ors.add(cb.like(expr, likeValue));
            }
            return cb.or(ors.toArray(new Predicate[0]));
        };
    }

    private static <E> Specification<E> filterSpec(SearchFilter f) {
        return (root, query, cb) -> {
            Path<Object> path = root.get(f.field());
            String op = Optional.ofNullable(f.operator()).orElse("eq").toLowerCase(Locale.ROOT);
            String value = f.value();

            return switch (op) {
                case "eq" -> cb.equal(path, cast(path, value));
                case "ne" -> cb.notEqual(path, cast(path, value));
                case "like" -> cb.like(cb.lower(path.as(String.class)), "%" + value.toLowerCase(Locale.ROOT) + "%");
                case "gt" -> cb.greaterThan(path.as(Comparable.class), (Comparable) cast(path, value));
                case "lt" -> cb.lessThan(path.as(Comparable.class), (Comparable) cast(path, value));
                case "ge" -> cb.greaterThanOrEqualTo(path.as(Comparable.class), (Comparable) cast(path, value));
                case "le" -> cb.lessThanOrEqualTo(path.as(Comparable.class), (Comparable) cast(path, value));
                case "in" -> path.in(Arrays.stream(value.split(","))
                        .map(v -> cast(path, v.trim()))
                        .collect(Collectors.toSet()));
                default -> throw new IllegalArgumentException("Desteklenmeyen operator: " + op);
            };
        };
    }

    private static Object cast(Path<?> path, String value) {
        Class<?> type = path.getJavaType();
        if (type.equals(String.class)) return value;
        if (type.equals(Long.class)) return Long.valueOf(value);
        if (type.equals(Integer.class)) return Integer.valueOf(value);
        if (Enum.class.isAssignableFrom(type)) {
            @SuppressWarnings({"rawtypes", "unchecked"})
            Object e = Enum.valueOf((Class<Enum>) type, value);
            return e;
        }
        if (type.equals(Boolean.class)) return Boolean.valueOf(value);
        // Gerekirse başka türler ekleyin.
        return value;
    }

}
