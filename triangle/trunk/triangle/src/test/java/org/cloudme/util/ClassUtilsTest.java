package org.cloudme.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassUtilsTest {
    @Test
    public void testIsString() {
        assertTrue(ClassUtils.isString(String.class));
    }

    @Test
    public void testIsNumber() {
        assertTrue(ClassUtils.isNumber(int.class));
    }
}
