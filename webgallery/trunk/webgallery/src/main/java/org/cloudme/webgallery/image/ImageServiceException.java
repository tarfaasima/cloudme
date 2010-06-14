package org.cloudme.webgallery.image;


@SuppressWarnings( "serial" )
public class ImageServiceException extends RuntimeException {
    public ImageServiceException(Exception root) {
		super(root);
	}
}
