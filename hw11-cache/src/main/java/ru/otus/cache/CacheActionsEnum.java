package ru.otus.cache;

public enum CacheActionsEnum {
    PUT("put"),REMOVE("remove"), GET("get");
    private final String value;

    CacheActionsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
