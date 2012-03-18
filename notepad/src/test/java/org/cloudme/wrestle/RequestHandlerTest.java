package org.cloudme.wrestle;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;
import lombok.val;

import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.Mapping;
import org.junit.Test;

public class RequestHandlerTest {
    @Data
    public static class Person {
        private String name;
    }

    @Test
    public void testExecute() throws Throwable {
        val actionHandler = new ActionHandler() {
            @SuppressWarnings( "unused" )
            @Get
            public String hello(String name, int repeats, String greeting) {
                val sb = new StringBuilder();
                for (int i = 0; i < repeats; i++) {
                    sb.append(greeting == null ? "Hello" : greeting).append(", ").append(name).append("!\n");
                }
                return sb.toString();
            }
        };
        val method = actionHandler.getClass().getMethod("hello", String.class, int.class, String.class);
        val requestHandler = new RequestHandler(actionHandler, method, "/api/test");
        val result = requestHandler.execute("/api/test/World/3", null, null);
        assertEquals("Hello, World!\nHello, World!\nHello, World!\n", result);
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    @Test
    public void testExecuteWithMapping() throws Throwable {
        val actionHandler = new ActionHandler() {
            @SuppressWarnings( "unused" )
            @Get
            public String hello(@Mapping( "foo" ) Person person) {
                return "Hello, " + person.getName() + "!";
            }
        };
        val method = actionHandler.getClass().getMethod("hello", Person.class);
        val requestHandler = new RequestHandler(actionHandler, method, "/api/test");
        HttpServletRequest req = new MockHttpServletRequest();
        Map params = req.getParameterMap();
        params.put("foo.name", new String[] { "World" });
        val result = requestHandler.execute("/api/test/hello", req, null);
        assertEquals("Hello, World!", result);
    }

}
