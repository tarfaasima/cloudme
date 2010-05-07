package org.cloudme.triangle.validation;

/**
 * Validates a {@link String} based on the given configuration.
 * 
 * @author Moritz Petersen
 */
public class StringValidator extends AbstractValidator<String> {
    /**
     * Regular expression that must be matched by the given {@link String}.
     */
    @Override
    public void setMask(final String mask) {
        if (mask != null) {
            addCheck(new Check<String>() {
                public boolean perform(String value) {
                    return value.matches(mask);
                }
            });
        }
    }

    /**
     * Maximum (inclusive) length of the {@link String}
     */
    @Override
    public void setMax(final Double max) {
        if (max != null) {
            addCheck(new Check<String>() {
                public boolean perform(String value) {
                    return value.length() <= max;
                }
            });
        }
    }

    /**
     * Minimum (inclusive) length of the {@link String}.
     */
    @Override
    public void setMin(final Double min) {
        if (min != null) {
            addCheck(new Check<String>() {
                public boolean perform(String value) {
                    return value.length() >= min;
                }
            });
        }
    }
}
