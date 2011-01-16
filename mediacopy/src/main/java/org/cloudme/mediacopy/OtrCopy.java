package org.cloudme.mediacopy;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OtrCopy extends BaseCopy {
    private class Key {
        private final String title;
        private final Date date;

        public Key(String title, Date date) {
            this.title = title;
            this.date = date;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    private File originalsDir;
    private File cutDir;
    private File destDir;

    @Override
    public void copy() {
        // list all files
        Map<Key, OtrFile> originals = asMap(originalsDir.listFiles());
        // find files with cut version - remove uncut version from list
        // list all already copied files
        // copy only if not in list or timestamp / size different
    }

    private Map<Key, OtrFile> asMap(File[] files) {
        Map<Key, OtrFile> otrFiles = new HashMap<Key, OtrFile>();
        for (File file : files) {
            try {
                OtrFile otrFile = new OtrFile(file);
                otrFiles.put(new Key(otrFile.getTitle(), otrFile.getDate()), otrFile);
            }
            catch (IllegalStateException e) {
                // ignore
            }
        }
        return otrFiles;
    }

    public void setOriginalsDir(String dir) {
        originalsDir = new File(dir);
    }

    public void setCutDir(String dir) {
        cutDir = new File(dir);
    }

    public void setDestDir(String dir) {
        destDir = new File(dir);
    }
}
