package org.cloudme.mediacopy;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
			} catch (ClassCastException e) {
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

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm");

	private File originalsDir;
	private File cutDir;
	private File destDir;

	@Override
	public void copy() {
		Map<Key, OtrFile> originals = asMap(originalsDir.listFiles());
		Map<Key, OtrFile> cuts = asMap(cutDir.listFiles());
		Map<Key, OtrFile> existingFiles = readExisting();
		for (OtrFile otrFile : cuts.values()) {
			if (needsUpdate(otrFile, existingFiles)) {
				createCopy(otrFile, true);
			}
		}
		for (OtrFile otrFile : originals.values()) {
			if (!cuts.containsKey(new Key(otrFile))) {
				if (needsUpdate(otrFile, existingFiles)) {
					createCopy(otrFile, false);
				}
			}
		}
	}

	private HashMap<Key, OtrFile> readExisting() {
		throw new UnsupportedOperationException("Method readExisting() not implemented");
	}

	private void createCopy(OtrFile otrFile, boolean isCut) {
		// create folder structure
		File dir = new File(destDir, otrFile.getTitle().substring(0, 1).toUpperCase());
		dir.mkdirs();
		// copy file
		// write to log
		throw new UnsupportedOperationException("Method createCopy() not implemented");
	}

	private boolean needsUpdate(OtrFile otrFile, Map<Key, OtrFile> existingFiles) {
		OtrFile existingFile = existingFiles.get(new Key(otrFile));
		if (existingFile == null) {
			return true;
		}
		return otrFile.getFile().lastModified() != existingFile.getFile().lastModified();
	}

	private Map<Key, OtrFile> asMap(File[] files) {
		Map<Key, OtrFile> otrFiles = readExisting();
		for (File file : files) {
			try {
				OtrFile otrFile = new OtrFile(file);
				otrFiles.put(new Key(otrFile), otrFile);
			} catch (IllegalArgumentException e) {
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

	String createFileName(OtrFile o, boolean isCut) {
		return String.format("%1$s (%2$s, %3$tF %3$tH-%3$tM%4$s).%5$s", o.getTitle(), o.getChannel(), o.getDate(),
				(isCut ? ", CUT" : ""), o.getSuffix());
	}
}
