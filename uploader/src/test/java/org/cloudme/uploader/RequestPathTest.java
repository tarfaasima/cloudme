package org.cloudme.uploader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RequestPathTest {
    private static final String ENCODING = System.getProperty("file.encoding");

    @Test
    public void testIdOnly() {
        RequestPath path = new RequestPath("/12");
        assertTrue(path.isValid());
        assertEquals(new Long(12), path.getId());
        assertEquals("abc", path.getFileNameOr("abc", ENCODING));
    }

    @Test
    public void testInvalidId() {
        RequestPath path = new RequestPath("/12ab");
        assertFalse(path.isValid());
    }

    @Test
    public void testWithFileName() {
        RequestPath path = new RequestPath("/12/abc.jpg");
        assertTrue(path.isValid());
        assertEquals(new Long(12), path.getId());
        assertEquals("abc.jpg", path.getFileNameOr("xyz", ENCODING));
    }

    @Test
    public void testWithFileNameAndMore() {
        RequestPath path = new RequestPath("/12/234x344/abc.jpg");
        assertTrue(path.isValid());
        assertEquals(new Long(12), path.getId());
        assertEquals("abc.jpg", path.getFileNameOr("xyz", ENCODING));
    }
}
