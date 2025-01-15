package com.onetool.server.api.category;

import lombok.Getter;

@Getter
public enum FirstCategoryType {
    CATEGORY_BUILDING(1L, "building"),
    CATEGORY_CIVIL(2L, "civil"),
    CATEGORY_INTERIOR(3L, "interior"),
    CATEGORY_MACHINE(4L, "machine"),
    CATEGORY_ELECTRIC(5L, "electric"),
    CATEGORY_ETC(6L, "etc");

    private final Long categoryId;
    private final String type;

    FirstCategoryType(Long categoryId, String type) {
        this.categoryId = categoryId;
        this.type = type;
    }

    public static FirstCategoryType findByCategoryId(Long categoryId) {
        for (FirstCategoryType category : values()) {
            if (category.getCategoryId().equals(categoryId)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category ID: " + categoryId);
    }

    public static FirstCategoryType findByType(String type) {
        for (FirstCategoryType category : values()) {
            if (category.getType().equalsIgnoreCase(type)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category type: " + type);
    }
}