package org.cloudme.triangle.validation;

public class RequiredValidator implements Validator {
    @Override
    public void validate(Object object) {
        if (object == null) {
            throw new ValidationException();
        }
    }
}
