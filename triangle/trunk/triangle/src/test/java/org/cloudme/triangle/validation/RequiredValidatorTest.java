package org.cloudme.triangle.validation;

import org.junit.Test;


public class RequiredValidatorTest extends ValidatorTest<Object> {
    @Override
    protected Validator<Object> createValidator() {
        return new RequiredValidator();
    }

    @Test
    public void testValidateNull() {
        assertNotValid(null);
    };

    @Test
    public void testValid() {
        assertValid("hello");
    }

    @Test
    public void testNotValidBoolean() {
        assertNotValid(false);
    }
}
