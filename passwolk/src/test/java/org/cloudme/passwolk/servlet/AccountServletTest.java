package org.cloudme.passwolk.servlet;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;

import org.cloudme.passwolk.account.Account;
import org.cloudme.passwolk.account.AccountModule;
import org.cloudme.passwolk.account.AccountService;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class AccountServletTest extends AbstractServiceTestCase {
    @Inject
    private AccountService accountService;
    private AccountServlet accountServlet;
    private MockHttpServletRequest req;
    private MockHttpServletResponse resp;
    private Account account;

    @Before
    public void init() throws ServletException {
        accountServlet = new AccountServlet();
        accountServlet.init();
        req = new MockHttpServletRequest();
        resp = new MockHttpServletResponse();

        account = new Account();
        account.setDescription("This is a test account");
        account.setEmail("test@example.com");
        account.setLogin("user");
        account.setPassword("qwert");
        account.setTitle("Test Account");
    }

    @Test
    public void testDoGetHttpServletRequestHttpServletResponse() throws Exception {
        assertDoGet("[]");
        accountService.put(account);
        assertDoGet("[{\"id\":1,\"title\":\"Test Account\",\"email\":\"test@example.com\",\"description\":\"This is a test account\",\"login\":\"user\",\"password\":\"qwert\"}]");
    }

    private void assertDoGet(String expected) throws ServletException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resp.setWriter(new PrintWriter(out));
        accountServlet.doGet(req, resp);
        String output = out.toString();
        assertEquals(expected, output);
    }

    @Test
    public void testDoPostHttpServletRequestHttpServletResponse() throws Exception {
        req.setParameter("description", account.getDescription());
        req.setParameter("email", account.getEmail());
        req.setParameter("login", account.getLogin());
        req.setParameter("password", account.getPassword());
        req.setParameter("title", account.getTitle());
        
        accountServlet.doPost(req, resp);
        
        List<Account> accounts = accountService.listAll();
        assertEquals(1, accounts.size());
        assertEquals(account, accounts.get(0));
    }

    @Test
    public void testDoDeleteHttpServletRequestHttpServletResponse() throws Exception {
        accountService.put(account);

        req.setParameter("id", String.valueOf(account.getId()));
        accountServlet.doDelete(req, resp);

        assertEquals(0, accountService.listAll().size());
    }

    @Override
    protected Module[] getModules() {
        return new Module[] { new AccountModule() };
    }

}
