package org.cloudme.webgallery.flickr;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

public class FlickrRequest {
    public enum FlickrUrl {
        AUTH("http://flickr.com/services/auth/"), REST("http://api.flickr.com/services/rest/");

        private final String url;

        private FlickrUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return url;
        }
    }

    private final SortedMap<String, String> params = new TreeMap<String, String>();
    private String secret;
    private FlickrUrl url;

    public FlickrRequest append(String name, Object value) {
        params.put(name, value.toString());
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
        UrlBuilder urlBuilder = new UrlBuilder(url.toString());
        SignatureBuilder sigBuilder = new SignatureBuilder(secret);
        if (!params.isEmpty()) {
            for (Entry<String, String> param : params.entrySet()) {
                String name = param.getKey();
                String value = param.getValue();
                urlBuilder.append(name, value);
                sigBuilder.append(name, value);
            }
        }
        urlBuilder.append("api_sig", sigBuilder.toSignature());
        return urlBuilder.toUrl();
    }
}
