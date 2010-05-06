package org.cloudme.triangle.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ValidatorFactoryTest {
    @Test
    public void testValidatorTypeNotNull() {
        assertValidator(StringValidator.class, null, StringValidator.class);
    }

    @Test
    public void testValidatorTypeNull() {
        assertValidator(null, String.class, StringValidator.class);
    }

    @Test
    public void testNumericType() {
        assertValidator(null, byte.class, NumberValidator.class);
        assertValidator(null, short.class, NumberValidator.class);
        assertValidator(null, int.class, NumberValidator.class);
        assertValidator(null, long.class, NumberValidator.class);
        assertValidator(null, float.class, NumberValidator.class);
        assertValidator(null, double.class, NumberValidator.class);
        assertValidator(null, Integer.class, NumberValidator.class);
        assertValidator(null, Float.class, NumberValidator.class);
    }

    private static void assertValidator(Class<? extends Validator> validatorType,
            Class<?> type,
            Class<? extends Validator> expected) {
        final Validator validator = ValidatorFactory.newInstanceFor(validatorType, type);
        if (expected == null) {
            assertNull(validator);
        }
        else {
            assertEquals(expected, validator.getClass());
        }
    }
}
