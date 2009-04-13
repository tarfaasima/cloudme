package org.cloudme.metamodel.util;

public class ValidationError {
    private final String value;
    private final String property;

    ValidationError(String value, String property) {
        this.value = value;
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public String getProperty() {
        return property;
    }
}
