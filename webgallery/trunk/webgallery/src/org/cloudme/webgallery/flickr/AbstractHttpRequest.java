package org.cloudme.webgallery.flickr;

import java.net.URLConnection;

abstract class AbstractHttpRequest implements HttpRequest {
    public abstract void writeTo(URLConnection con);

    public abstract String getUrl();

    abstract void append(String name, String value);

    abstract void append(String string, String filename, String contentType, byte[] data);
}
