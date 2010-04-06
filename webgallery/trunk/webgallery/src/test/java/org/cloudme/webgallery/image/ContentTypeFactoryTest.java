package org.cloudme.webgallery.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class ContentTypeFactoryTest {
    @Test
    public void testGetByFilename() {
        assertEquals(ContentType.JPEG, ContentTypeFactory.getContentTypeByFileName("hello/world.jpg"));
        assertNull(ContentTypeFactory.getContentTypeByFileName("hello/readme.txt"));
    }
}
