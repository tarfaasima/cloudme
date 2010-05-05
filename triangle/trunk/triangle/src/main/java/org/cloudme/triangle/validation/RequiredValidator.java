package org.cloudme.triangle.validation;

/**
 * Checks if the value is not null.
 * 
 * @author Moritz Petersen
 */
public class RequiredValidator extends AbstractValidator {
    /**
     * Checks if the value is not null.
     */
    @Override
    public void validate(Object value) {
        if (value == null) {
            throw new ValidationException();
        }
    }
}
