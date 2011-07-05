package org.cloudme.passwolk.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloudme.passwolk.account.Account;

public class AccountServlet extends AbstractGuiceServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = parseRequest(req, new Account(), "account");
    }
}
