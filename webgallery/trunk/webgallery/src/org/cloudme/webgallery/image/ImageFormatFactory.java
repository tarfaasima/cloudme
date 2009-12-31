package org.cloudme.webgallery.image;

import java.util.HashMap;
import java.util.Map;

public class ImageFormatFactory {
    private static Map<String, ImageFormat> imageFormatMap;
    
    public static ImageFormat getImageFormat(String id) {
        if (imageFormatMap == null) {
            imageFormatMap = new HashMap<String, ImageFormat>();
            for (ImageFormat imageFormat : ImageFormat.values()) {
                imageFormatMap.put(imageFormat.getId(), imageFormat);
            }
        }
        return imageFormatMap.get(id);
    }
}
