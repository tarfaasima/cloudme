package org.cloudme.passwolk.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloudme.passwolk.account.Account;
import org.cloudme.passwolk.account.AccountModule;
import org.cloudme.passwolk.account.AccountService;
import org.cloudme.passwolk.json.JsonWriter;
import org.cloudme.passwolk.util.StringUtils;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * Provides access to {@link Account}s.
 * <p>
 * The {@link #doGet(HttpServletRequest, HttpServletResponse)} method returns a
 * list of all {@link Account}s.
 * <p>
 * The {@link #doPost(HttpServletRequest, HttpServletResponse)} method saves an
 * {@link Account}.
 * <p>
 * The {@link #doDelete(HttpServletRequest, HttpServletResponse)} method deletes
 * an account.
 * <p>
 * 
 * @author Moritz Petersen
 */
@SuppressWarnings( "serial" )
public class AccountServlet extends HttpServlet {
    @Inject
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        Guice.createInjector(new AccountModule()).injectMembers(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonWriter writer = new JsonWriter(resp.getWriter());
        writer.write(accountService.findAll());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = new Account();
        String id = req.getParameter("id");
        if (!StringUtils.isEmpty(id)) {
            try {
                account.setId(Long.valueOf(id));
            }
            catch (NumberFormatException e) {
                throw new ServletException("Wrong id.", e);
            }
        }
        account.setDescription(req.getParameter("description"));
        account.setEmail(req.getParameter("email"));
        account.setLogin(req.getParameter("login"));
        account.setPassword(req.getParameter("password"));
        account.setTitle(req.getParameter("title"));
        System.out.println(account);
        accountService.put(account);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.valueOf(req.getParameter("id"));
        accountService.delete(id);
    }
}
