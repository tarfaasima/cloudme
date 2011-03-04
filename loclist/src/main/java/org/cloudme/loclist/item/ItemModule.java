package org.cloudme.loclist.item;

import com.google.inject.AbstractModule;

public class ItemModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ItemDao.class);
        bind(ItemService.class);
    }
}
