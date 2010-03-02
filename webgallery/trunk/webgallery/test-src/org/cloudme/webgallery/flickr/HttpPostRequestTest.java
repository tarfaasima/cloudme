package org.cloudme.webgallery.flickr;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

import net.sourceforge.stripes.action.Before;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;


public class HttpPostRequestTest {
    private URLConnection con;

    @Before
    public void setUp() {
        class DummyConnection extends URLConnection {
            private ByteArrayOutputStream out;
            
            public DummyConnection() {
                super(null);
            }
            
            @Override
            public void setDoOutput(boolean dooutput) {
            }
            
            @Override
            public void connect() throws IOException {
            }
            
            @Override
            public OutputStream getOutputStream() throws IOException {
                out = new ByteArrayOutputStream();
                return out;
            }
            
            @Override
            public String toString() {
                return new String(out.toByteArray());
            }
        }
        con = new DummyConnection();
    }
    
    @Test
    public void testPostRequest() {
        AbstractHttpRequest req = new HttpPostRequest("http://something.com/api/upload");
        req.writeTo(con);
        System.out.println(con);
    }
}
