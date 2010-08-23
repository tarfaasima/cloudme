package de.moritzpetersen.homepage.dataimport;

import de.moritzpetersen.homepage.domain.Entry;

public interface EntryHandler {
    void handle(Entry entry);
}
