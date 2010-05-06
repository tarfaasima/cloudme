package org.cloudme.triangle.validation;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public abstract class ValidatorTest {
    protected Validator validator;
    private final Double max;
    private final Double min;
    private final Object invalidMax;
    private final Object invalidMin;
    private final Object valid;
    private final String mask;
    private final Object invalidMask;

    public ValidatorTest() {
        this(null, null, null, null, null, null, null);
    }

    public ValidatorTest(String mask, Double max, Double min, Object invalidMask, Object invalidMax, Object invalidMin, Object valid) {
        this.mask = mask;
        this.max = max;
        this.min = min;
        this.invalidMask = invalidMask;
        this.invalidMax = invalidMax;
        this.invalidMin = invalidMin;
        this.valid = valid;

    }

    protected abstract Validator createValidator();

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

    void assertValid(Object value) {
        validator.validate(value);
    }

    void assertNotValid(Object value) {
        try {
            validator.validate(value);
            fail();
        }
        catch (final ValidationException e) {
            // expected
        }
    }
}
