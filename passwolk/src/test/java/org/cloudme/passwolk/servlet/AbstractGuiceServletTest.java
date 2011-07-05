package org.cloudme.passwolk.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cloudme.passwolk.account.Account;
import org.junit.Test;

public class AbstractGuiceServletTest {
    @Test
    public void testParseRequest() {
        final Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("account.name", "Test Name");
        HttpServletRequest req = new MockHttpServletRequest() {
            @Override
            public String getParameter(String name) {
                return parameterMap.get(name);
            }
        };
        AbstractGuiceServlet servlet = new AbstractGuiceServlet() {
        };
        Account account = servlet.parseRequest(req, new Account(), "account");
        assertEquals("Test Name", account.getName());
        assertNull(account.getId());
    }
}
