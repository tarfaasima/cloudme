package org.cloudme.triangle.validation;

/**
 * Checks if the value is not null. For a boolean it also checks if the value is
 * true.
 * 
 * @author Moritz Petersen
 */
public class RequiredValidator extends AbstractValidator<Object> {
    /**
     * Checks if the value is not null. For a boolean it also checks if the
     * value is true.
     */
    @Override
    public void validate(Object value) {
        if (value == null) {
            throw new ValidationException();
        }
        if (value instanceof Boolean) {
            if (!((Boolean) value)) {
                throw new ValidationException();
            }
        }
    }
}
