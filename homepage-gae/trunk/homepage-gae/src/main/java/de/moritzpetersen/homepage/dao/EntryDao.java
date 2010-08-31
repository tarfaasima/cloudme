package de.moritzpetersen.homepage.dao;

import de.moritzpetersen.homepage.domain.Entry;

public class EntryDao extends BaseDao<Entry> {
    public EntryDao() {
        super(Entry.class);
    }
}
