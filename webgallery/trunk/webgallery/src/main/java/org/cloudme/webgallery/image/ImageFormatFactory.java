package org.cloudme.webgallery.image;

import java.util.HashMap;
import java.util.Map;

public class ImageFormatFactory {
    private static Map<String, ImageFormat> imageFormatMap;
    
    public synchronized static ImageFormat getImageFormat(String id) {
        if (imageFormatMap == null) {
            imageFormatMap = new HashMap<String, ImageFormat>();
            for (DefaultImageFormat imageFormat : DefaultImageFormat.values()) {
                imageFormatMap.put(imageFormat.getId(), imageFormat);
            }
        }
        ImageFormat format = imageFormatMap.get(id);
        if (format == null) {
            format = new DynamicImageFormat(id);
            imageFormatMap.put(id, format);
        }
        if (format == null) {
            throw new IllegalStateException("format is null " + id);
        }
        return format;
    }
}
