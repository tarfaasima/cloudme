package org.cloudme.triangle.validation;

import org.junit.Test;

public class EmailValidatorTest extends ValidatorTest {
    @Test
    public void testValidate() {
        assertValid("test@example.com");
        assertNotValid("no valid@email");
    }

    @Override
    protected Validator createValidator() {
        return new EmailValidator();
    }
}
