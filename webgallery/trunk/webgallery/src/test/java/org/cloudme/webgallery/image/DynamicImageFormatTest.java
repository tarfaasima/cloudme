package org.cloudme.webgallery.image;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class DynamicImageFormatTest {
    @Test
    public void testParse() {
        assertImageFormat(640, 480, true, "640x480");
        assertImageFormat(640, 480, false, "640#480");
    }
    
    private static void assertImageFormat(int expectedWidth, int expectedHeight, boolean expectedIsCrop, String input) {
        DynamicImageFormat format = new DynamicImageFormat(input);
        assertEquals(expectedWidth, format.getWidth());
        assertEquals(expectedHeight, format.getHeight());
        assertEquals(expectedIsCrop, format.isCrop());
    }
}
