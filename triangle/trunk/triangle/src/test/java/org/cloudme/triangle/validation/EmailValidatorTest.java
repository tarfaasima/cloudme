package org.cloudme.triangle.validation;

import org.junit.Test;

public class EmailValidatorTest extends ValidatorTest<String> {
    @Test
    public void testValidate() {
        assertValid("test@example.com");
        assertNotValid("no valid@email");
    }

    @Override
    protected Validator<String> createValidator() {
        return new EmailValidator();
    }
}
