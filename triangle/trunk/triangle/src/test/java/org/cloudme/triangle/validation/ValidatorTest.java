package org.cloudme.triangle.validation;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public abstract class ValidatorTest<T> {
    protected Validator<T> validator;
    private final String mask;
    private final Double max;
    private final Double min;
    private final T invalidMax;
    private final T invalidMin;
    private final T valid;
    private final T invalidMask;

    public ValidatorTest() {
        this(null, null, null, null, null, null, null);
    }

    public ValidatorTest(String mask, Double max, Double min, T invalidMask, T invalidMax, T invalidMin, T valid) {
        this.mask = mask;
        this.max = max;
        this.min = min;
        this.invalidMask = invalidMask;
        this.invalidMax = invalidMax;
        this.invalidMin = invalidMin;
        this.valid = valid;

    }

    protected abstract Validator<T> createValidator();

    @Before
    public void setUp() {
        validator = createValidator();
    }

    @Test
    public void testValidateMask() {
        if (mask == null) {
            return;
        }
        validator.setMask(mask);
        assertValid(valid);
    }

    @Test
    public void testInvalidMask() {
        if (invalidMask == null) {
            return;
        }
        validator.setMask(mask);
        assertNotValid(invalidMask);
    }

    @Test
    public void testInvalidMax() {
        if (max == null) {
            return;
        }
        validator.setMax(max);
        assertNotValid(invalidMax);
    }

    @Test
    public void testInvalidMin() {
        if (min == null) {
            return;
        }
        validator.setMin(min);
        assertNotValid(invalidMin);
    }

    @Test
    public void testInValidRange() {
        if (max == null || min == null) {
            return;
        }
        validator.setMax(max);
        validator.setMin(min);
        assertValid(valid);
    }

    void assertValid(T value) {
        validator.validate(value);
    }

    void assertNotValid(T value) {
        try {
            validator.validate(value);
            fail();
        }
        catch (final ValidationException e) {
            // expected
        }
    }
}
