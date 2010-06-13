package org.cloudme.webgallery.image;


public class ImageServiceException extends RuntimeException {
	private static final long serialVersionUID = -733351605500608720L;

    public ImageServiceException(Exception root) {
		super(root);
	}
}
