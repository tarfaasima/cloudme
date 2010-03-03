package org.cloudme.webgallery.flickr;

import java.net.URLConnection;

class HttpGetRequest implements AppendableHttpRequest {
    private final StringBuilder urlBuilder;
    private boolean hasParams;

    HttpGetRequest(String url) {
        urlBuilder = new StringBuilder(url);
    }

    public void writeTo(URLConnection con) {
        // do nothing
    }

    public String getUrl() {
        return urlBuilder.toString();
    }

    public void append(String name, String value) {
        urlBuilder.append(hasParams ? "&" : "?").append(name).append("=").append(value);
        hasParams = true;
    }
    
    public void append(String string, String filename, String contentType, byte[] data) {
        throw new UnsupportedOperationException("Appending binary data not supported for HttpGetRequest.");
    }
}
