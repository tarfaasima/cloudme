package org.cloudme.triangle.validation;


public class StringValidatorTest extends ValidatorTest {
    public StringValidatorTest() {
        super("[^x]*", 10D, 3D, "xxx", "Hello World", "a", "abcd");
    }

    @Override
    protected Validator createValidator() {
        return new StringValidator();
    }
}
