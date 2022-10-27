package com.se306.youseesoft.Enums;

/**
 * An enum class representing the categories
 */
public enum Category {
    Phone("Phone"), Tablet("Tablet"), Laptop("Laptop");
    private final String categoryName;
    Category(final String name) {
        categoryName = name;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
