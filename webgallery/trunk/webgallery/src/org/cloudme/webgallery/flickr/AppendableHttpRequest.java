package org.cloudme.webgallery.flickr;


interface AppendableHttpRequest extends HttpRequest {
    void append(String name, String value);

    void append(String string, String filename, String contentType, byte[] data);
}
