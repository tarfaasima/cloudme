package org.cloudme.cashbox.stripes.action;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.cashbox.account.AccountService;
import org.cloudme.cashbox.domain.Account;
import org.cloudme.gaestripes.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/action/account/{$event}/{account.id}" )
public class AccountActionBean extends AbstractActionBean {
    @Inject
    private AccountService accountService;
    @ValidateNestedProperties( { @Validate( field = "name", required = true ) } )
    private Account account;
    private Collection<Account> accounts;

    @DefaultHandler
    @DontValidate
    public Resolution show() {
        accounts = accountService.list();
        return resolve("account.jsp");
    }

    public Resolution create() {
        accountService.create(account);
        return redirect("/action/account");
    }

    @DontValidate
    public Resolution delete() {
        accountService.delete(account);
        return redirect("/action/account");
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }
}
