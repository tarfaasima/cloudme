package org.cloudme.triangle.validation;

/**
 * Validates a {@link String} based on the given configuration.
 * 
 * @author Moritz Petersen
 */
public class StringValidator implements Validator {
    private String mask;
    private Integer max;
    private Integer min;

    /**
     * Regular expression that must be matched by the given {@link String}.
     */
    @Override
    public void setMask(String mask) {
        this.mask = mask;
    }

    /**
     * Maximum (inclusive) length of the {@link String}
     */
    @Override
    public void setMax(double max) {
        this.max = ((Double) max).intValue();
    }

    /**
     * Minimum (inclusive) length of the {@link String}.
     */
    @Override
    public void setMin(double min) {
        this.min = ((Double) min).intValue();
    }

    /**
     * Validates the {@link String}. Validation of length and matching pattern
     * is performed only if defined.
     */
    @Override
    public void validate(Object value) {
        final String str = (String) value;
        if (mask != null) {
            if (!str.matches(mask)) {
                throw new ValidationException();
            }
        }
        if (max != null) {
            if (str.length() > max) {
                throw new ValidationException();
            }
        }
        if (min != null) {
            if (str.length() < min) {
                throw new ValidationException();
            }
        }
    }
}
