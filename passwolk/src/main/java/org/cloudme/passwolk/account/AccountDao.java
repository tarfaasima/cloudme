package org.cloudme.passwolk.account;

import org.cloudme.sugar.AbstractDao;

/**
 * The DAO of the {@link Account} entity; will be used only in the Account
 * module.
 * 
 * @author Moritz Petersen
 */
class AccountDao extends AbstractDao<Account> {
    public AccountDao() {
        super(Account.class);
    }
}
