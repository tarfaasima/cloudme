package org.cloudme.webgallery.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DynamicImageFormatTest {
    @Test
    public void testParse() {
        DynamicImageFormat format = new DynamicImageFormat("320x640");
        assertEquals(320, format.getWidth());
        assertEquals(640, format.getHeight());
        assertTrue(format.isCrop());
    }
}
