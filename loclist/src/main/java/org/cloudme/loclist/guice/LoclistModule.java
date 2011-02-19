package org.cloudme.loclist.guice;

import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.NoteItemDao;
import org.cloudme.loclist.dao.NoteDao;
import org.cloudme.loclist.dao.ItemIndexDao;
import org.cloudme.loclist.dao.LocationDao;
import org.cloudme.loclist.dao.UpdateDao;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.location.LocationService;

import com.google.inject.AbstractModule;

public class LoclistModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ItemDao.class);
        bind(NoteItemDao.class);
        bind(NoteDao.class);
        bind(ItemIndexDao.class);
        bind(LocationDao.class);
        // bind(TickDao.class);
        bind(UpdateDao.class);

        bind(ItemService.class);
        bind(LocationService.class);
    }
}
