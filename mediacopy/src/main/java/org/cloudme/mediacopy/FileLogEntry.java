package org.cloudme.mediacopy;

import java.io.File;

public class FileLogEntry {
    private final String name;
    private final long lastModified;

    public FileLogEntry(File file) {
        name = file.getName();
        lastModified = file.lastModified();
    }

    public FileLogEntry(String name, long lastModified) {
        this.name = name;
        this.lastModified = lastModified;
    }

    public String getName() {
        return name;
    }

    public long getLastModified() {
        return lastModified;
    }
}
