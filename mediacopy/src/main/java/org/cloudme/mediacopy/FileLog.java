package org.cloudme.mediacopy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileLog {
	private final Map<String, Long> entries = new HashMap<String, Long>();

    public void load() {
    }

    public void save() {
    }

    public boolean contains(File file) {
		Long lastModified = entries.get(file.getName());
		if (lastModified == null) {
			return false;
		}
		return file.lastModified() == (long) lastModified;
    }

	public void put(File file) {
		entries.put(file.getName(), file.lastModified());
    }
}
