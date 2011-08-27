package org.cloudme.uploader;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class RequestPath {
    private Long id;
    private String fileName;

    public RequestPath(String path) {
        if (path.startsWith("/")) {
            String[] segments = getPathSegments(path.substring(1));
            try {
                if (segments.length > 0) {
                    id = new Long(segments[0]);
                }
                if (segments.length > 1) {
                    fileName = segments[segments.length - 1];
                }
            }
            catch (NumberFormatException e) {
                // ignore
            }
        }
    }

    private String[] getPathSegments(String path) {
        String[] segments = path.split("/");
        if (segments.length == 0) {
            segments = new String[] { path };
        }
        return segments;
    }

    public Long getId() {
        return id;
    }

    public boolean isValid() {
        return id != null;
    }

    public String getFileNameOr(String name, String encoding) {
        try {
            return fileName == null ? name : URLDecoder.decode(fileName, encoding);
        }
        catch (UnsupportedEncodingException e) {
            return fileName;
        }
    }
}
