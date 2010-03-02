package org.cloudme.webgallery.flickr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

public class FlickrRequest {
    public enum FlickrUrl {
        AUTH("http://flickr.com/services/auth/"),
        REST("http://api.flickr.com/services/rest/"),
        UPLOAD("http://api.flickr.com/services/upload/");

        private final String url;

        private FlickrUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return url;
        }
    }
    
    private class PhotoData {
        private final String contentType;
        private final String filename;
        private final byte[] data;

        public PhotoData(String contentType, String filename, byte[] data) {
            this.contentType = contentType;
            this.filename = filename;
            this.data = data;
        }
    }

    private final SortedMap<String, String> params = new TreeMap<String, String>();
    private final Collection<PhotoData> photos = new ArrayList<PhotoData>();
    private String secret;
    private FlickrUrl url;

    public void add(String name, Object value) {
        if (value != null) {
            params.put(name, value.toString());
        }
    }
    
    public void addPhoto(String contentType, String filename, byte[] data) {
        photos.add(new PhotoData(contentType, filename, data));
    }

    public void addApiKey(String apiKey) {
        add("api_key", apiKey);
    }

    public void addSecret(String secret) {
        this.secret = secret;
    }

    public void addMethod(String method) {
        add("method", method);
    }

    public void setUrl(FlickrUrl url) {
        this.url = url;
    }
    
    public HttpRequest getHttpRequest() {
        AbstractHttpRequest req = photos.isEmpty() ? new HttpGetRequest(url.toString()) : new HttpPostRequest(url.toString());
        SignatureBuilder sigBuilder = new SignatureBuilder(secret);
        for (Entry<String, String> param : params.entrySet()) {
            String name = param.getKey();
            String value = param.getValue();
            req.append(name, value);
            sigBuilder.append(name, value);
        }
        for (PhotoData photo : photos) {
            req.append("photo", photo.filename, photo.contentType, photo.data);
        }
        req.append("api_sig", sigBuilder.toSignature());
        return req;
    }
}
