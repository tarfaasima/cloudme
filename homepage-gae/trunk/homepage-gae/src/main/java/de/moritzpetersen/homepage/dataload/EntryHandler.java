package de.moritzpetersen.homepage.dataload;

import de.moritzpetersen.homepage.domain.Entry;

public interface EntryHandler {
    void handle(Entry entry);
}
