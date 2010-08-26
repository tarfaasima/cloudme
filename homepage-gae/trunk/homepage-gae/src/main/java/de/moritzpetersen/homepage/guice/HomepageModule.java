package de.moritzpetersen.homepage.guice;

import com.google.inject.AbstractModule;

import de.moritzpetersen.homepage.dataimport.DataLoader;
import de.moritzpetersen.homepage.dataimport.DefaultEntryHandler;
import de.moritzpetersen.homepage.dataimport.DefaultSourceHandler;
import de.moritzpetersen.homepage.dataimport.EntryHandler;
import de.moritzpetersen.homepage.dataimport.SourceHandler;

public class HomepageModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataLoader.class);
        bind(EntryHandler.class).to(DefaultEntryHandler.class);
        bind(SourceHandler.class).to(DefaultSourceHandler.class);
    }
}
