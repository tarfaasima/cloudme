package org.cloudme.webgallery.tags;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.cloudme.webgallery.Album;

public class Functions {
    public static String coyprightYear(String year, String separator) {
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());
        return year + (currentYear.equals(year) ? "" : separator + currentYear);
    }
    
    public static String url(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
        if (obj instanceof Album) {
            return albumUrl((Album) obj);
        }
        throw new IllegalArgumentException("Unsupported type: " + obj.getClass());
    }

    private static String albumUrl(Album album) {
        return "/gallery/album/" + album.getId();
    }
}
