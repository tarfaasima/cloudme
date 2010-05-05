package org.cloudme.triangle.validation;

/**
 * Validates a {@link Number} based on the given configuration. The
 * {@link #setMask(String)} method is not supported.
 * 
 * @author Moritz Petersen
 */
public class NumberValidator extends AbstractValidator {
    /**
     * The maximum number (inclusive). Optional.
     */
    private Double max;
    /**
     * The minimum number (inclusive). Optional.
     */
    private Double min;

    /**
     * The maximum number (inclusive). Optional.
     */
    @Override
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * The minimum number (inclusive). Optional.
     */
    @Override
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * Validates the value.
     */
    @Override
    public void validate(Object value) {
        final Number num = (Number) value;
        if (max != null) {
            if (num.doubleValue() > max) {
                throw new ValidationException();
            }
        }
        if (min != null) {
            if (num.doubleValue() < min) {
                throw new ValidationException();
            }
        }
    }
}
