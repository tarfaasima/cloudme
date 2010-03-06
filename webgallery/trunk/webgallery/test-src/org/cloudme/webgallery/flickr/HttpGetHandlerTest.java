package org.cloudme.webgallery.flickr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class HttpGetHandlerTest {
    @Test
    public void testRequest() {
        HttpHandler handler = new HttpGetHandler("http://some.url.org/api/auth/");
        handler.append("say", "Hello World!");
        assertEquals("http://some.url.org/api/auth/?say=Hello+World%21", handler.getRequest().getURI().toString());
    }
}
