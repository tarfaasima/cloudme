package org.cloudme.mediacopy;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.inject.Inject;

public abstract class BaseCopy {
	private File destDir;
	private FileLog fileLog;
	@Inject
	private CopyListener copyListener;

	public final void copy() {
		fileLog.load();
		Iterable<CopyParam> params = prepareCopy();
		long size = 0;
		int count = 0;
		fireCopyInitialized();
		for (CopyParam param : params) {
			size += param.getSrc().length();
			count++;
		}
		fireCopyPrepared(size, count);
		for (CopyParam param : params) {
			File src = param.getSrc();
			File dest = param.getDest();
			fireCopyStarted(src, dest);
			if (!fileLog.contains(src)) {
				try {
					copyFile(src, dest);
					fileLog.put(src);
					fireCopySuccess(src, dest);
				} catch (IOException e) {
					fireCopyFailed(src, dest, e.getMessage());
				}
			} else {
				fireCopySkipped(src, dest);
			}
		}
		fireCopyCompleted();
		fileLog.save();
	}

	private void fireCopySkipped(File src, File dest) {
		if (copyListener == null) {
			return;
		}
		copyListener.copySkipped(src, dest);
	}

	private void fireCopyInitialized() {
		if (copyListener == null) {
			return;
		}
		copyListener.copyInitialized();
	}

	private void fireCopyCompleted() {
		if (copyListener == null) {
			return;
		}
		copyListener.copyCompleted();
	}

	private void fireCopyFailed(File src, File dest, String message) {
		if (copyListener == null) {
			return;
		}
		copyListener.copyFailed(src, dest, message);
	}

	private void fireCopySuccess(File src, File dest) {
		if (copyListener == null) {
			return;
		}
		copyListener.copySuccess(src, dest);
	}

	private void fireCopyStarted(File src, File dest) {
		if (copyListener == null) {
			return;
		}
		copyListener.copyStarted(src, dest);
	}

	private void fireCopyPrepared(long size, int count) {
		if (copyListener == null) {
			return;
		}
		copyListener.copyPrepared(count, size);
	}

	protected abstract Iterable<CopyParam> prepareCopy();

	private void copyFile(File src, File dest) throws IOException {
		FileUtils.copyFile(src, dest, false);
	}

	public File getDestDir() {
		return destDir;
	}

	public void setDestDir(String dir) {
		destDir = new File(dir);
	}

	public void setFileLog(FileLog fileLog) {
		this.fileLog = fileLog;
	}
}
