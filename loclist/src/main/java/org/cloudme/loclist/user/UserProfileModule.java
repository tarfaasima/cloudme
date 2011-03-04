package org.cloudme.loclist.user;

import com.google.inject.AbstractModule;

public class UserProfileModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserProfileDao.class);
        bind(UserProfileService.class);
    }
}
