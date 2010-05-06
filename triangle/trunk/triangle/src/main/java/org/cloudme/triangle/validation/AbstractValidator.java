package org.cloudme.triangle.validation;

/**
 * Dummy base class for implementing {@link Validator}s. All methods throw an
 * {@link UnsupportedOperationException} and need to be overridden by inheriting
 * classes.
 * 
 * @author Moritz Petersen
 */
public abstract class AbstractValidator implements Validator {
    /**
     * Empty implementation.
     * 
     * @throws UnsupportedOperationException
     */
    @Override
    public void setMask(String mask) {
        throw new UnsupportedOperationException();
    }

    /**
     * Empty implementation.
     * 
     * @throws UnsupportedOperationException
     */
    @Override
    public void setMax(double max) {
        throw new UnsupportedOperationException();
    }

    /**
     * Empty implementation.
     * 
     * @throws UnsupportedOperationException
     */
    @Override
    public void setMin(double min) {
        throw new UnsupportedOperationException();
    }
}
