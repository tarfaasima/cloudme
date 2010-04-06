package org.cloudme.webgallery.tags;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class FunctionsTest {
    @Test
    public void testAppVersion() {
        assertEquals("22", Functions.appVersion("test-src/test-appengine-web.xml"));
    }
}
