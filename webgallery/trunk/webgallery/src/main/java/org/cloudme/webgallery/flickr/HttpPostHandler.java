package org.cloudme.webgallery.flickr;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

public class HttpPostHandler implements HttpHandler {
    private final HttpPost post;
    private final MultipartEntity multipart;

    public HttpPostHandler(String url) {
        post = new HttpPost(url);
        multipart = new MultipartEntity();
    }

    public void append(String name, String value) {
        try {
            multipart.addPart(name, new StringBody(value));
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public void append(String name, String filename, String contentType, byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        multipart.addPart(name, new InputStreamBody(in, contentType, filename));
    }

    public HttpUriRequest getRequest() {
        post.setEntity(multipart);
        return post;
    }
}
