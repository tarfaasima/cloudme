package org.cloudme.triangle.annotation;

import static org.junit.Assert.fail;

import org.cloudme.triangle.Entity;
import org.cloudme.triangle.EntityResolver;
import org.cloudme.triangle.TriangleController;
import org.cloudme.triangle.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;


public class ValidateTest {
    private TriangleController controller;
    private Entity entity;

    @Before
    public void setUp() {
        controller = new TriangleController();
        controller.setEntityResolver(new EntityResolver() {
            @Override
            public void addEntity(Entity entity) {
                ValidateTest.this.entity = entity;
            }
        });
    }

    @Test
    public void testValidateStringWithMask() {
        class TestClass {
            @SuppressWarnings( "unused" )
            @Validate( mask = "[abc]*" )
            String value;
        }
        controller.addEntity(TestClass.class);

        TestClass testClass = new TestClass();
        testClass.value = "abc";
        entity.validate(testClass);
    }

    @Test
    public void testValidateStringWithoutValidateAnnotation() {
        class TestClass {
            @SuppressWarnings( "unused" )
            String value;
        }
        controller.addEntity(TestClass.class);

        TestClass testClass = new TestClass();
        entity.validate(testClass);

        testClass.value = "abc";
        entity.validate(testClass);
    }

    @Test
    public void testValidateBooleanWithEmptyAnnotation() {
        class TestClass {
            @SuppressWarnings( "unused" )
            @Validate
            boolean value;
        }
        controller.addEntity(TestClass.class);

        TestClass testClass = new TestClass();
        try {
            entity.validate(testClass);
            fail();
        }
        catch (ValidationException e) {
            // expected
        }

        testClass.value = true;
        entity.validate(testClass);
    }
}
