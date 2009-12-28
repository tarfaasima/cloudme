package org.cloudme.webgallery.image;

public interface ImageService {
    byte[] process(byte[] data, ImageParameter parameter, String format);
}
