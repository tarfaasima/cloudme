package org.cloudme.wrestle;

import static org.junit.Assert.assertEquals;

import javax.servlet.ServletException;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.val;

import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.Param;
import org.cloudme.wrestle.annotation.Post;
import org.cloudme.wrestle.annotation.UrlMapping;
import org.junit.Before;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.servletunit.ServletRunner;

public class RequestHandlerTest {
    private static enum Method {
        GET {
            @Override
            WebRequest getWebRequest(String urlString) {
                return new GetMethodWebRequest(urlString);
            }
        },
        POST {
            @Override
            WebRequest getWebRequest(String urlString) {
                return new PostMethodWebRequest(urlString);
            }
        };

        abstract WebRequest getWebRequest(String urlString);
    }

    private static ActionHandler actionHandler;
    private ServletRunner sr;

    @Data
    public static class Person {
        private String name;
    }

    public static class TestController extends WrestleController {
        @Override
        public void init() throws ServletException {
            registerActionHandler(actionHandler);
        }
    }

    @Before
    public void setUp() {
        actionHandler = null;
        sr = new ServletRunner();
    }

    @Test
    public void testExecute() throws Throwable {
        @UrlMapping( "app" )
        class TestActionHandler implements ActionHandler {
            @Get
            public String hello(String name, int repeats, String greeting) {
                val sb = new StringBuilder();
                for (int i = 0; i < repeats; i++) {
                    sb.append(greeting == null ? "Hello" : greeting).append(", ").append(name).append("!\n");
                }
                return sb.toString();
            }
        }
        runServletTest("\"Hello, World!\\nHello, World!\\nHello, World!\\n\"", new TestActionHandler(),
                "app/hello/World/3", Method.GET);
    }

    @Test
    public void testExecuteWithMapping() throws Throwable {
        @UrlMapping( "app" )
        class TestActionHandler implements ActionHandler {
            @Post
            public String hello(@Param( name = "foo" ) Person person) {
                return "Hello, " + person.getName() + "!";
            }
        }
        runServletTest("\"Hello, World!\"", new TestActionHandler(), "app/hello", Method.POST, "foo.name", "World");
    }

    @Test
    public void testExecuteWithSimpleMapping() throws Throwable {
        @UrlMapping("app")
        class TestActionHandler implements ActionHandler {
            @Get
            public String hello(@Param( name = "name" ) String name) {
                return "Hello, " + name + "!";
            }
        }

        runServletTest("\"Hello, World!\"", new TestActionHandler(), "app/hello", Method.GET, "name", "World");
    }

    @SneakyThrows
    private void runServletTest(String expected, ActionHandler handler, String path, Method method, String... params) {
        RequestHandlerTest.actionHandler = handler;

        sr.registerServlet(path, TestController.class.getName());
        val sc = sr.newClient();
        val req = method.getWebRequest("http://www.example.com/" + path);
        if (params != null) {
            for (int i = 0; i < params.length; i += 2) {
                req.setParameter(params[i], params[i + 1]);
            }
        }

        val resp = sc.getResponse(req);
        assertEquals(expected, resp.getText());
    }
}
