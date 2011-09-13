package org.cloudme.passwolk.account;

import com.google.inject.AbstractModule;

public class AccountModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountDao.class);
        bind(AccountService.class);
    }

}
