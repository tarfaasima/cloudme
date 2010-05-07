package org.cloudme.triangle.validation;


public class StringValidatorTest extends ValidatorTest<String> {
    public StringValidatorTest() {
        super("[^x]*", 10D, 3D, "xxx", "Hello World", "a", "abcd");
    }

    @Override
    protected Validator<String> createValidator() {
        return new StringValidator();
    }
}
