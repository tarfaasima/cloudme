package org.cloudme.mediacopy;

import java.io.File;

public class CopyParam {
	private File src;
	private File dest;

	public CopyParam(File src, File dest) {
		this.src = src;
		this.dest = dest;
	}

	public File getSrc() {
		return src;
	}

	public File getDest() {
		return dest;
	}
}
