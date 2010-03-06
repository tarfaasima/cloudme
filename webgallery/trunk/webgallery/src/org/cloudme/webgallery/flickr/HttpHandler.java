package org.cloudme.webgallery.flickr;

import org.apache.http.client.methods.HttpUriRequest;

public interface HttpHandler {
    void append(String name, String value);

    void append(String name, String filename, String contentType, byte[] data);

    HttpUriRequest getRequest();
}
