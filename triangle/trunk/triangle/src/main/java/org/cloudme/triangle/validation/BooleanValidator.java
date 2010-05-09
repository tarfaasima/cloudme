package org.cloudme.triangle.validation;

/**
 * Validates boolean values. Accepts only {@link Boolean#TRUE}.
 * 
 * @author Moritz Petersen
 */
public class BooleanValidator extends AbstractValidator<Boolean> {
    @Override
    public void validate(Boolean value) {
        if (!value.booleanValue()) {
            throw new ValidationException(this, value);
        }
    }
}
