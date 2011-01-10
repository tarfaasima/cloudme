package org.cloudme.cashbox.dao;

import org.cloudme.cashbox.domain.Account;
import org.cloudme.gaestripes.AbstractDao;

public class AccountDao extends AbstractDao<Account> {
    public AccountDao() {
        super(Account.class);
    }
}
