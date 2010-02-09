package org.cloudme.webgallery.image;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class ImageServiceException extends RuntimeException {
	private static final long serialVersionUID = -733351605500608720L;
	private final byte[] data;

	public ImageServiceException(ImageFormat format, Exception root) {
		super(root);
		data = getOverQuotaData(format);
	}

	public byte[] getImageData() {
		return data;
	}

	private byte[] getOverQuotaData(ImageFormat format) {
		int max = Math.max(format.getWidth(), format.getHeight());
		String overQuotaFileName = "over_quota_" + max + ".jpg";
		try {
			InputStream in = getClass().getResourceAsStream(overQuotaFileName);
			if (in == null) {
				in = getClass().getResourceAsStream("over_quota_198.jpg");
			}
			return IOUtils.toByteArray(in);
		} catch (IOException e) {
			return null;
		}
	}
}
