package org.cloudme.wrestle;

import static org.junit.Assert.assertEquals;

import javax.servlet.ServletException;

import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.UrlMapping;
import org.junit.Test;

import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;

public class WrestleControllerTest {
    @UrlMapping( "test" )
    public static class TestActionHandler implements ActionHandler {
        @Get
        public String greet(String name) {
            return "Hello " + name + "!";
        }
    }

    @UrlMapping( "api" )
    public static class TestController extends WrestleController {
        @Override
        public void init() throws ServletException {
            registerActionHandler(new TestActionHandler());
        }
    }

    @Test
    public void testController() throws Exception {
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("/api/test/greet/World", TestController.class.getName());
        WebResponse resp = sr.getResponse("http://www.example.com/api/test/greet/World");
        assertEquals("\"Hello World!\"", resp.getText());
    }

}
