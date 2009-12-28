package org.cloudme.webgallery.image;

import java.util.HashMap;
import java.util.Map;

public class ImageParameterFactory {
    private static Map<String, ImageParameter> imageParameterMap;
    
    public static ImageParameter getImageParameter(String id) {
        if (imageParameterMap == null) {
            imageParameterMap = new HashMap<String, ImageParameter>();
            for (ImageParameter imageParameter : ImageParameter.values()) {
                imageParameterMap.put(imageParameter.getId(), imageParameter);
            }
        }
        return imageParameterMap.get(id);
    }
}
