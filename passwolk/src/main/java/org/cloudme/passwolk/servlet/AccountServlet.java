package org.cloudme.passwolk.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloudme.passwolk.account.Account;
import org.cloudme.passwolk.account.AccountService;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class AccountServlet extends AbstractGuiceServlet {
    @Inject
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Account account = parseRequest(req, new Account(), "account");
        System.out.println("account.id = " + account.getId());
        accountService.put(account);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Account> accounts = accountService.listAll();
        if (accounts.isEmpty()) {
            accounts.add(createDummyAccount());
            accounts.add(createDummyAccount());
        }
        resp.getWriter().print(new Gson().toJson(accounts));
    }

    private Account createDummyAccount() {
        Account account = new Account();
        account.setEmail("test@user.com");
        account.setId(1L);
        account.setLogin("test");
        account.setName("Test Account");
        account.setPassword("secret123");
        account.setUrl("http://my.url.com");
        return account;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.getLong(req.getParameter("id"));
        accountService.delete(id);
    }
}
