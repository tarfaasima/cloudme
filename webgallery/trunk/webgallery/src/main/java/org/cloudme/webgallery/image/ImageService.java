package org.cloudme.webgallery.image;

public interface ImageService {
    byte[] process(byte[] data, ImageFormat format, ContentType type, float balance);
}
