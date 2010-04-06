package org.cloudme.webgallery.image;

import java.io.Serializable;

public interface ImageFormat extends Serializable {
    int getWidth();
    int getHeight();
    boolean isCrop();
}
