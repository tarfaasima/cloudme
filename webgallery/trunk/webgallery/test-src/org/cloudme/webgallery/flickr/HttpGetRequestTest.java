package org.cloudme.webgallery.flickr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.junit.Test;


public class HttpGetRequestTest {
    @Test
    public void testGetRequest() throws UnsupportedEncodingException {
        AppendableHttpRequest req = new HttpGetRequest("http://something.org/api/upload");
        assertEquals("http://something.org/api/upload", req.getUrl());
        req.append("test", "something");
        assertEquals("http://something.org/api/upload?test=something", req.getUrl());
        req.append("say", URLEncoder.encode("Hello world!", "UTF-8"));
        assertEquals("http://something.org/api/upload?test=something&say=Hello+world%21", req.getUrl());
        URLConnection con = new DummyConnection();
        req.writeTo(con);
        assertFalse(con.getDoOutput());
    }
}
