package org.cloudme.triangle.validation;

import org.junit.Test;


public class RequiredValidatorTest extends ValidatorTest {
    @Override
    protected Validator createValidator() {
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
