package de.moritzpetersen.homepage.service;

import java.util.List;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dao.EntryDao;
import de.moritzpetersen.homepage.domain.Entry;

public class EntryService {
    @Inject
    private EntryDao dao;

    public List<Entry> getEntries() {
        return dao.findAll();
    }
}
