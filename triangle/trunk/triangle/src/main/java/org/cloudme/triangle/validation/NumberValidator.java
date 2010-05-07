package org.cloudme.triangle.validation;

/**
 * Validates a {@link Number} based on the given configuration. The
 * {@link #setMask(String)} method is not supported.
 * 
 * @author Moritz Petersen
 */
public class NumberValidator extends AbstractValidator<Number> {
    /**
     * The maximum number (inclusive). Optional.
     */
    @Override
    public void setMax(final Double max) {
        if (max != null) {
            addCheck(new Check<Number>() {
                public boolean perform(Number value) {
                    return value.doubleValue() <= max;
                }
            });
        }
    }

    /**
     * The minimum number (inclusive). Optional.
     */
    @Override
    public void setMin(final Double min) {
        if (min != null) {
            addCheck(new Check<Number>() {
                public boolean perform(Number value) {
                    return value.doubleValue() >= min;
                }
            });
        }
    }
}
