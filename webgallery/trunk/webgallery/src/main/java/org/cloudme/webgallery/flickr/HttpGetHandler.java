package org.cloudme.webgallery.flickr;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class HttpGetHandler implements HttpHandler {
    private final List<NameValuePair> params = new ArrayList<NameValuePair>();
    private final String url;

    public HttpGetHandler(String url) {
        this.url = url;
    }

    public void append(String name, String value) {
        params.add(new BasicNameValuePair(name, value));
    }

    public void append(String name, String filename, String contentType, byte[] data) {
        throw new UnsupportedOperationException("HttpGetHandler does not support binary data.");
    }

    public HttpUriRequest getRequest() {
        return new HttpGet(url + "?" + URLEncodedUtils.format(params, "UTF-8"));
    }
}
