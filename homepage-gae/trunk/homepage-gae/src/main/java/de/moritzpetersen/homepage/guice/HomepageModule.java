package de.moritzpetersen.homepage.guice;

import com.google.inject.AbstractModule;

import de.moritzpetersen.homepage.dao.EntryDao;
import de.moritzpetersen.homepage.dao.SourceDao;
import de.moritzpetersen.homepage.dataload.DataLoader;
import de.moritzpetersen.homepage.dataload.DefaultEntryHandler;
import de.moritzpetersen.homepage.dataload.DefaultSourceHandler;
import de.moritzpetersen.homepage.dataload.EntryHandler;
import de.moritzpetersen.homepage.dataload.SourceHandler;

public class HomepageModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataLoader.class);
        bind(EntryHandler.class).to(DefaultEntryHandler.class);
        bind(SourceHandler.class).to(DefaultSourceHandler.class);
        bind(EntryDao.class);
        bind(SourceDao.class);
    }
}
