package de.moritzpetersen.homepage.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;


public class MailHandlerServletTest {
    @Test
    public void testDoPost() throws ServletException, IOException {
        MailHandlerServlet servlet = new MailHandlerServlet();
        HttpServletRequest req = new AbstractHttpServletRequest() {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStream() {
                    InputStream in = new ByteArrayInputStream("123".getBytes());

                    @Override
                    public int read() throws IOException {
                        return in.read();
                    }
                };
            }
        };
        servlet.doPost(req, null);
    }
}
