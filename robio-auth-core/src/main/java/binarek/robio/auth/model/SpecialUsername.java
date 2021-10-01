package binarek.robio.auth.model;

import java.util.Arrays;

public enum SpecialUsername implements Username {

    DEFAULT_ADMIN("admin"),
    ;

    private final String value;

    public static SpecialUsername of(String value) {
        return Arrays.stream(values())
                .filter(username -> username.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Special username " + value + " does not exist"));
    }

    SpecialUsername(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
