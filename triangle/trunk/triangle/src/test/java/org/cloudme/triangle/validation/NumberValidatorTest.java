package org.cloudme.triangle.validation;


public class NumberValidatorTest extends ValidatorTest<Number> {
    public NumberValidatorTest() {
        super(null, 10D, 8D, null, 100, 3, 9);
    }

    @Override
    protected Validator<Number> createValidator() {
        return new NumberValidator();
    }
}
