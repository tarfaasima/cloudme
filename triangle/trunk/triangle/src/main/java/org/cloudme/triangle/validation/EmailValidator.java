package org.cloudme.triangle.validation;

public class EmailValidator extends StringValidator {
    private static final String REGEX = "[^@]*@[^.]*\\..*";

    public EmailValidator() {
        setMask(REGEX);
    }
}
