package org.cloudme.triangle.annotation;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import junit.framework.AssertionFailedError;

import org.cloudme.triangle.validation.ValidationException;
import org.cloudme.triangle.validation.Validator;
import org.junit.Test;

public class ValidateTest {
    @Test
    public void testValidateStringWithMask() {
        class TestClass {
            @SuppressWarnings( "unused" )
            @Validate( mask = "[abc]*" )
            String value;
        }
        TestClass testClass = new TestClass();
        testClass.value = "abc";
        assertValid(testClass);
    }

    @Test
    public void testValidateBooleanWithEmptyAnnotation() {
        class TestClass {
            @SuppressWarnings( "unused" )
            @Validate
            boolean value;
        }
        TestClass testClass = new TestClass();
        assertNotValid(testClass);

        testClass.value = true;
        assertValid(testClass);
    }

    @Test
    public void testValidateNumberWithMinMax() {
        class TestClass {
            @SuppressWarnings( "unused" )
            @Validate( min = 3, max = 10 )
            double value;
        }
        TestClass testClass = new TestClass();
        assertNotValid(testClass);

        testClass.value = 11;
        assertNotValid(testClass);

        testClass.value = 1;
        assertNotValid(testClass);

        testClass.value = 10;
        assertValid(testClass);
    }

    private void assertNotValid(Object obj) {
        try {
            assertValid(obj);
            fail();
        } catch (ValidationException e) {
            // expected
        }
    }

    @SuppressWarnings( "unchecked" )
    private void assertValid(Object obj) {
        try {
            Field field = obj.getClass().getDeclaredField("value");
            Validator validator = Validate.Factory.newInstance(field);
            validator.validate(field.get(obj));
        } catch (SecurityException e) {
            throw new AssertionFailedError(e.getMessage());
        } catch (NoSuchFieldException e) {
            throw new AssertionFailedError(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new AssertionFailedError(e.getMessage());
        }
    }
}
