package org.cloudme.loclist.guice;

import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.ItemInstanceDao;
import org.cloudme.loclist.dao.ItemListDao;
import org.cloudme.loclist.dao.LocationDao;
import org.cloudme.loclist.dao.TickDao;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.location.LocationService;

import com.google.inject.AbstractModule;

public class LoclistModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ItemDao.class);
        bind(ItemInstanceDao.class);
        bind(ItemListDao.class);
        bind(LocationDao.class);
        bind(TickDao.class);

        bind(ItemService.class);
        bind(LocationService.class);
    }
}
