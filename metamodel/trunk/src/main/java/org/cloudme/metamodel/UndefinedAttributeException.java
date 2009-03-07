package org.cloudme.metamodel;

public class UndefinedAttributeException extends RuntimeException {
    public UndefinedAttributeException(Attribute attribute) {
        super("Undefined attribute: " + attribute);
    }
}
