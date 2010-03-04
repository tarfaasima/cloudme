// created 03.03.2010 19:17:04 by Moritz Petersen
package org.cloudme.webgallery.service;

import org.cloudme.webgallery.image.ImageFormat;

class FlickrImageFormat implements ImageFormat {
    public int getHeight() {
        return 500;
    }

    public int getWidth() {
        return 500;
    }

    public boolean isCrop() {
        return false;
    }
}