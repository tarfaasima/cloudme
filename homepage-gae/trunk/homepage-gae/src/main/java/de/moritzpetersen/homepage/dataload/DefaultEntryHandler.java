package de.moritzpetersen.homepage.dataload;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dao.EntryDao;
import de.moritzpetersen.homepage.domain.Entry;

public class DefaultEntryHandler implements EntryHandler {
    @Inject
    private EntryDao entryDao;

    @Override
    public void handle(Entry entry) {
        entryDao.save(entry);
    }
}
