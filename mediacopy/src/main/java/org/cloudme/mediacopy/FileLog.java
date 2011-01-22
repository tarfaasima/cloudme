package org.cloudme.mediacopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileLog {
	private final Map<String, Long> entries = new HashMap<String, Long>();
    private final File logFile;

    public FileLog(File logFile) {
        this.logFile = logFile;
    }

    @SuppressWarnings( "unchecked" )
    public void load() {
        if (!logFile.exists()) {
            return;
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(logFile));
            entries.clear();
            entries.putAll((Map<? extends String, ? extends Long>) in.readObject());
            in.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            File dir = logFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(logFile));
            out.writeObject(entries);
            out.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean contains(File file) {
		Long lastModified = entries.get(file.getName());
		if (lastModified == null) {
			return false;
		}
		return file.lastModified() == lastModified;
    }

	public void put(File file) {
		entries.put(file.getName(), file.lastModified());
    }
}
