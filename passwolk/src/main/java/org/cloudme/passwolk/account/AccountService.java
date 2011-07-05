package org.cloudme.passwolk.account;

import org.cloudme.sugar.AbstractService;

import com.google.inject.Inject;

public class AccountService extends AbstractService<Account> {
    @Inject
    public AccountService(AccountDao accountDao) {
        super(accountDao);
    }
}
