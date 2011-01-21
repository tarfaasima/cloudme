package org.cloudme.mediacopy;

import java.io.File;

public interface CopyListener {
	void copyPrepared(int count, long size);

	void copyStarted(File src, File dest);

	void copyFailed(File src, File dest, String message);

	void copySuccess(File src, File dest);

	void copyCompleted();

	void copyInitialized();

	void copySkipped(File src, File dest);
}
