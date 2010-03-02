package org.cloudme.webgallery.flickr;

import java.net.URLConnection;

public interface HttpRequest {
    String getUrl();

    void writeTo(URLConnection con);
}
