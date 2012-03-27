package org.cloudme.wrestle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.UrlMapping;
import org.junit.Test;

public class ControllerTest {
    @UrlMapping( "A" )
    private static class HandlerA implements ActionHandler {
        @Get
        public String hello(String name) {
            return "Hello, " + name + "!";
        }
    }

    @Test
    public void testDoGetHttpServletRequestHttpServletResponse() throws Throwable {
        Controller controller = new Controller() {
        };
        controller.register(new HandlerA());

        controller.init();

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setPathInfo("/A/hello/World");
        MockHttpServletResponse resp = new MockHttpServletResponse();
        StringWriter stringWriter = new StringWriter();
        resp.setWriter(new PrintWriter(stringWriter));

        controller.doGet(req, resp);
        
        resp.getWriter().flush();
        
        assertEquals("\"Hello, World!\"", stringWriter.toString());
    }

    @Test
    public void testDoPostHttpServletRequestHttpServletResponse() {
        fail("Not yet implemented");
    }

}
