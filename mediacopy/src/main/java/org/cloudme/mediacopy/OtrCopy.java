package org.cloudme.mediacopy;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class OtrCopy extends BaseCopy {
    private class Key {
        private final String title;
        private final Date date;

        public Key(OtrFile otrFile) {
            this.title = otrFile.getTitle();
            this.date = otrFile.getDate();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            try {
                Key key = (Key) obj;
                return new EqualsBuilder().append(title, key.title).append(date, key.date).isEquals();
            }
            catch (ClassCastException e) {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(title).append(date).toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(title).append(date).toString();
        }
    }

    private File originalsDir;
    private File cutDir;
    private File destDir;
    private File logFile;

    @Override
    public void copy() {
        FileLog fileLog = new FileLog(logFile);
        fileLog.load();
		Map<Key, OtrFile> cuts = asMap(cutDir);
        for (OtrFile otrFile : cuts.values()) {
            createCopy(fileLog, otrFile, true);
        }
		Map<Key, OtrFile> originals = asMap(originalsDir);
        for (OtrFile otrFile : originals.values()) {
            if (!cuts.containsKey(new Key(otrFile))) {
                createCopy(fileLog, otrFile, false);
            }
        }
        fileLog.save();
    }

    private void createCopy(FileLog fileLog, OtrFile otrFile, boolean isCut) {
        if (!fileLog.contains(otrFile.getFile())) {
            boolean success = createCopy(otrFile, isCut);
            if (success) {
                fileLog.put(otrFile.getFile());
            }
        }
    }

    private boolean createCopy(OtrFile otrFile, boolean isCut) {
        File dir = new File(destDir, otrFile.getTitle().substring(0, 1).toUpperCase());
        dir.mkdirs();
        File dest = new File(dir, otrFile.createFileName(isCut));
        try {
            FileUtils.copyFile(otrFile.getFile(), dest, false);
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    private Map<Key, OtrFile> asMap(File dir) {
        Map<Key, OtrFile> otrFiles = new HashMap<Key, OtrFile>();
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.charAt(0) != '.';
            }
        });
        if (files != null) {
            for (File file : files) {
                try {
                    OtrFile otrFile = new OtrFile(file);
                    otrFiles.put(new Key(otrFile), otrFile);
                }
                catch (IllegalArgumentException e) {
                    // ignore
                }
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

    public void setLogFile(String name) {
        logFile = new File(name);
    }

}
