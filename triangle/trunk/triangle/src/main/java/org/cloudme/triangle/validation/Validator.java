package org.cloudme.triangle.validation;

/**
 * Interface for {@link Validator}s. See the specific {@link Validator}
 * implementations for further information.
 * 
 * @author Moritz Petersen
 */
public interface Validator {
    /**
     * A mask that must be matched by the given value. Optional.
     * 
     * @param mask
     *            A mask that must be matched by the given value. Optional.
     */
    void setMask(String mask);

    /**
     * The maximum value of the given value. Optional.
     * 
     * @param max
     *            The maximum value of the given value. Optional.
     */
    void setMax(double max);

    /**
     * The minimum value of the given value. Optional.
     * 
     * @param min
     *            The minimum value of the given value. Optional.
     */
    void setMin(double min);

    /**
     * Validates the value.
     * 
     * @param value
     *            The value that is validated.
     * @throws ValidationException
     *             on error.
     */
    void validate(Object value);
}
