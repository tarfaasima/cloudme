package org.cloudme.webgallery.tags;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class FunctionsTest {
    @Test
    public void testAppVersion() {
        assertEquals("22", Functions.appVersion("src/test/resources/test-appengine-web.xml"));
    }
}
