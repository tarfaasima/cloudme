package de.moritzpetersen.homepage.guice;

import com.google.inject.AbstractModule;

import de.moritzpetersen.homepage.dao.EntryDao;
import de.moritzpetersen.homepage.dataload.DataLoader;
import de.moritzpetersen.homepage.dataload.DefaultEntryHandler;
import de.moritzpetersen.homepage.dataload.EntryHandler;

public class HomepageModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataLoader.class);
        bind(EntryHandler.class).to(DefaultEntryHandler.class);
        bind(EntryDao.class);
    }
}
