package org.cloudme.passwolk.account;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

/**
 * The Guice {@link Module} that provides bindings for:
 * <ul>
 * <li>{@link AccountDao}
 * <li>{@link AccountService}
 * </ul>
 * 
 * @author Moritz Petersen
 */
public class AccountModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountDao.class);
        bind(AccountService.class);
    }

}
