package org.cloudme.webgallery.flickr;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.mail.internet.MimeMultipart;

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
    
    private class PostData {
        private final String contentType;
        private final String filename;
        private final byte[] data;

        public PostData(String contentType, String filename, byte[] data) {
            this.contentType = contentType;
            this.filename = filename;
            this.data = data;
        }
    }

    private final SortedMap<String, String> getParams = new TreeMap<String, String>();
    private final SortedMap<String, PostData> postParams = new TreeMap<String, PostData>();
    private String secret;
    private FlickrUrl url;
    private MimeMultipart multipart;

    public FlickrRequest append(String name, Object value) {
        if (value != null) {
            getParams.put(name, value.toString());
        }
        return this;
    }
    
    public FlickrRequest appendPost(String name, String contentType, String filename, byte[] data) {
        postParams.put(name, new PostData(contentType, filename, data));
        return this;
    }

    public FlickrRequest apiKey(String apiKey) {
        append("api_key", apiKey);
        return this;
    }

    public FlickrRequest secret(String secret) {
        this.secret = secret;
        return this;
    }

    public FlickrRequest method(String method) {
        append("method", method);
        return this;
    }

    public FlickrRequest url(FlickrUrl url) {
        this.url = url;
        return this;
    }

    public String toUrl() {
        boolean isPost = !postParams.isEmpty();
        UrlBuilder urlBuilder = new UrlBuilder(url.toString(), isPost);
        SignatureBuilder sigBuilder = new SignatureBuilder(secret);
        PostBuilder postBuilder = new PostBuilder(isPost);
        if (!getParams.isEmpty()) {
            for (Entry<String, String> param : getParams.entrySet()) {
                String name = param.getKey();
                String value = param.getValue();
                urlBuilder.append(name, value);
                sigBuilder.append(name, value);
                postBuilder.append(name, value);
            }
        }
        for (Entry<String, PostData> postParam : postParams.entrySet()) {
            String key = postParam.getKey();
            PostData value = postParam.getValue();
            postBuilder.append(key, value.contentType, value.filename, value.data);
        }
        String apiSig = sigBuilder.toSignature();
        urlBuilder.append("api_sig", apiSig);
        postBuilder.append("api_sig", apiSig);
        multipart = postBuilder.getMultipart();
        return urlBuilder.toUrl();
    }
    
    public MimeMultipart getMultipart() {
        return multipart;
    }
}
