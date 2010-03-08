package org.cloudme.webgallery.util;

import java.text.MessageFormat;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.model.Photo;

public class UrlUtils {
    private static final String HOST_URL_PATTERN = "http://{0}{1,choice,79#:{1,number,0}|80#|80<:{1,number,0}}";
    private static final String ALBUM_URL_PATTERN = "/gallery/album/{0}";
    private static final String PHOTO_URL_PATTERN = ALBUM_URL_PATTERN + "/photo/{1}";
    private static final MessageFormat HOST_URL_FORMAT = new MessageFormat(HOST_URL_PATTERN);
    private static final MessageFormat ALBUM_URL_FORMAT = new MessageFormat(ALBUM_URL_PATTERN);
    private static final MessageFormat PHOTO_URL_FORMAT = new MessageFormat(PHOTO_URL_PATTERN);

    private UrlUtils() {
        throw new UnsupportedOperationException("Don't instantiate UrlUtils!");
    }

    private static String createUrl(String host, int port) {
        return host == null ? "" : HOST_URL_FORMAT.format(new Object[] { host, port });
    }

    public static String createUrl(String host, int port, Photo photo) {
        return createUrl(host, port) + createUrl(photo);
    }

    public static String createUrl(Photo photo) {
        return PHOTO_URL_FORMAT.format(new Object[] { photo.getAlbumId(), photo.getId() });
    }

    public static String createUrl(String host, int port, Album album) {
        return createUrl(host, port) + createUrl(album);
    }

    public static String createUrl(Album album) {
        return ALBUM_URL_FORMAT.format(new Object[] { album.getId() });
    }

    public static boolean isLocal(String host) {
        return "localhost".equalsIgnoreCase(host) || "127.0.0.1".equals(host);
    }
}
