package org.cloudme.uploader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class UploaderServletTest {
    @Test
    public void testValidateId() throws Exception {
        UploaderServlet servlet = new UploaderServlet();
        assertEquals(new Long(12), invoke(servlet, "validateId", "12"));
        assertNull(invoke(servlet, "validateId", "12/abc"));
        assertNull(invoke(servlet, "validateId", ""));
    }

    private <T> T invoke(Object obj, String name, Object... param) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Method method = UploaderServlet.class.getDeclaredMethod(name, String.class);
        method.setAccessible(true);
        @SuppressWarnings( "unchecked" )
        T id = (T) method.invoke(obj, param);
        return id;
    }
}
