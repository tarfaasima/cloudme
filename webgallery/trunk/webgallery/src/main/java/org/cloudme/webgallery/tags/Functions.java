package org.cloudme.webgallery.tags;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.util.UrlUtils;

public class Functions {
    private static final Logger LOG = Logger.getLogger(Functions.class);
    private static final Pattern APP_VERSION_REGEX = Pattern.compile("<version>([^<]*)</version>");

    public static String coyprightYear(String year, String separator) {
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());
        return year + (currentYear.equals(year) ? "" : separator + currentYear);
    }

    public static String url(Object obj) {
        if (obj instanceof Photo) {
            Photo photo = (Photo) obj;
            return UrlUtils.createUrl(photo);
        }
        if (obj instanceof Album) {
            Album album = (Album) obj;
            return UrlUtils.createUrl(album);
        }
        throw new IllegalStateException("Unknown type for URL: " + obj.getClass().getName());
    }

    public static String appVersion(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line;
            while ((line = in.readLine()) != null) {
                Matcher m = APP_VERSION_REGEX.matcher(line);
                if (m.find()) {
                    return m.group(1);
                }
            }
        }
        catch (IOException e) {
            LOG.error("Unable to read app version.", e);
        }
        return "unknown";
    }
}
