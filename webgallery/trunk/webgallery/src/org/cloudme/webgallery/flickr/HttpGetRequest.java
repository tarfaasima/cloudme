package org.cloudme.webgallery.flickr;

import java.net.URLConnection;

class HttpGetRequest extends AbstractHttpRequest {
    private final StringBuilder urlBuilder;
    private boolean hasParams;

    HttpGetRequest(String url) {
        urlBuilder = new StringBuilder(url);
    }

    @Override
    public void writeTo(URLConnection con) {
        // do nothing
    }

    @Override
    public String getUrl() {
        return urlBuilder.toString();
    }

    @Override
    void append(String name, String value) {
        urlBuilder.append(hasParams ? "&" : "?").append(name).append("=").append(value);
        hasParams = true;
    }
    
    @Override
    void append(String string, String filename, String contentType, byte[] data) {
        throw new UnsupportedOperationException("Appending binary data not supported for HttpGetRequest.");
    }
}
