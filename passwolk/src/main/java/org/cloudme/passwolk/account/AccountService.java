package org.cloudme.passwolk.account;

import org.cloudme.sugar.AbstractService;

import com.google.inject.Inject;

/**
 * Provides public access to the {@link Account} entity.
 * 
 * @author Moritz Petersen
 */
public class AccountService extends AbstractService<Account> {
    @Inject
    public AccountService(AccountDao dao) {
        super(dao);
    }
}
