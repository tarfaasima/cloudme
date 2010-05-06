package org.cloudme.triangle.validation;


public class NumberValidatorTest extends ValidatorTest {
    public NumberValidatorTest() {
        super(null, 10D, 8D, null, 100, 3, 9);
    }

    @Override
    protected Validator createValidator() {
        return new NumberValidator();
    }
}
