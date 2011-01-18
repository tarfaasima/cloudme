package org.cloudme.mediacopy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileLog {
    private final Map<String, FileLogEntry> entries = new HashMap<String, FileLogEntry>();

    public void load() {
    }

    public void save() {
    }

    public boolean contains(File file) {
        FileLogEntry entry = entries.get(file.getName());
        if (entry == null) {
            return false;
        }
        return file.lastModified() == entry.getLastModified();
    }

    public void put(FileLogEntry entry) {
        entries.put(entry.getName(), entry);
    }
}
