package org.cloudme.mediacopy;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
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

	private File originalsDir;
	private File cutDir;

	@Override
	protected Iterable<CopyParam> prepareCopy() {
		Collection<CopyParam> params = new ArrayList<CopyParam>();
		Map<Key, OtrFile> cuts = asMap(cutDir);
		for (OtrFile otrFile : cuts.values()) {
			params.add(createCopyParam(otrFile, true));
		}
		for (OtrFile otrFile : asMap(originalsDir).values()) {
			if (!cuts.containsKey(new Key(otrFile))) {
				params.add(createCopyParam(otrFile, false));
			}
		}
		return params;
	}

	private CopyParam createCopyParam(OtrFile otrFile, boolean isCut) {
		String firstLetter = otrFile.getTitle().substring(0, 1).toUpperCase();
		File dest = new File(getDestDir(), firstLetter + File.separatorChar + otrFile.createFileName(isCut));
		File src = otrFile.getFile();
		return new CopyParam(src, dest);
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
				} catch (IllegalArgumentException e) {
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

}
