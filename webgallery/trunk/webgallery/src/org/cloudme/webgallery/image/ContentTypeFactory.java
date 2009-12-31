package org.cloudme.webgallery.image;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeFactory {
    private static Map<String, ContentType> contentTypeMap;

    public static ContentType getContentType(String type) {
        if (contentTypeMap == null) {
            contentTypeMap = new HashMap<String, ContentType>();
            for (ContentType contentType : ContentType.values()) {
                for (String suffix : contentType.getSuffixes()) {
                    contentTypeMap.put(suffix, contentType);
                }
            }
        }
        return contentTypeMap.get(type.trim().toUpperCase());
    }
}
