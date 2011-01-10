package org.cloudme.cashbox.account;

import java.util.Collection;

import org.cloudme.cashbox.dao.AccountDao;
import org.cloudme.cashbox.domain.Account;

import com.google.inject.Inject;

public class AccountService {
    @Inject
    private AccountDao accountDao;

    public Collection<Account> list() {
        return accountDao.listAll();
    }

    public void create(Account account) {
        accountDao.save(account);
    }
}
