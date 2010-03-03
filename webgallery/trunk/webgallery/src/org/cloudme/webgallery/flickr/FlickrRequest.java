package org.cloudme.webgallery.flickr;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Encapsulates a request to the Flickr API. Access to authentication, RESTful
 * API and photo upload are implemented. This class generates a
 * {@link HttpRequest} that can be used to call Flickr. This is an example for
 * requesting the authentication token:
 * 
 * <code><pre>
 * FlickrRequest req = new FlickrRequest();
 * req.setUrl(FlickUrl.REST);
 * req.addApiKey(key);
 * req.addSecret(secret);
 * req.addMethod("flickr.auth.getToken");
 * req.add("frob", frob);
 * </pre></code>
 * 
 * The {@link FlickrRequest} automatically signs the request if the secret is
 * provided by {@link #addSecret(String)}. It automatically creates a HTTP POST
 * request, if file data is set with the
 * {@link #addFile(String, String, byte[])} method.
 * 
 * @author Moritz Petersen
 */
public class FlickrRequest {
    /**
     * All available Flickr URLs.
     * 
     * @author Moritz Petersen
     * 
     */
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

    /**
     * A simple class containing file data.
     * 
     * @author Moritz Petersen
     */
    private class FileData {
        private final String contentType;
        private final String filename;
        private final byte[] data;

        /**
         * @param contentType
         *            The content type of the file.
         * @param filename
         *            The name of the file.
         * @param data
         *            Binary data.
         */
        public FileData(String contentType, String filename, byte[] data) {
            this.contentType = contentType;
            this.filename = filename;
            this.data = data;
        }
    }

    private static final String ENCODING = "UTF-8";
    private final SortedMap<String, String> params = new TreeMap<String, String>();
    private final Collection<FileData> files = new ArrayList<FileData>();
    private String secret;
    private FlickrUrl url;

    /**
     * Adds a parameter.
     * 
     * @param name
     *            The name of the parameter.
     * @param value
     *            The value of the parameter.
     */
    public void add(String name, Object value) {
        if (value != null) {
            params.put(name, enc(value));
        }
    }

    /**
     * Encodes the object after converting it to String.
     * 
     * @param value
     *            the object that is converted to String.
     * @return the encoded value.
     */
    private String enc(Object value) {
        try {
            return URLEncoder.encode(value.toString(), ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Adds binary data of a file. If this method is called, HTTP POST requests
     * are generated.
     * 
     * @param contentType
     *            The type of the file.
     * @param filename
     *            The name of the file.
     * @param data
     *            The binary data of the file.
     */
    public void addFile(String contentType, String filename, byte[] data) {
        files.add(new FileData(contentType, enc(filename), data));
    }

    /**
     * Convenience method to add the Flickr API key.
     * 
     * @param apiKey
     *            The API key.
     */
    public void addApiKey(String apiKey) {
        add("api_key", apiKey);
    }

    /**
     * Convenience method to add the Flickr secret.
     * 
     * @param secret
     *            The secret.
     */
    public void addSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Convenience method to add the Flickr auth token.
     * 
     * @param authToken
     *            The auth token.
     */
    public void addAuthToken(String authToken) {
        add("auth_token", authToken);
    }

    /**
     * Convenience method to add the Flickr API method.
     * 
     * @param method
     *            The API method.
     */
    public void addMethod(String method) {
        add("method", method);
    }

    /**
     * Sets the {@link FlickrUrl}.
     * 
     * @param url
     *            The URL.
     */
    public void setUrl(FlickrUrl url) {
        this.url = url;
    }

    /**
     * Creates the HTTP request.
     * 
     * @return The HTTP request. If binary data has been added this will be a
     *         HTTP POST request, otherwise HTTP GET. The request is
     *         automatically signed, if a secret has been provided.
     */
    public HttpRequest getHttpRequest() {
        AppendableHttpRequest req = files.isEmpty() ? new HttpGetRequest(url.toString()) : new HttpPostRequest(url
                .toString());
        SignatureBuilder sigBuilder = new SignatureBuilder(secret);
        for (Entry<String, String> param : params.entrySet()) {
            String name = param.getKey();
            String value = param.getValue();
            req.append(name, value);
            sigBuilder.append(name, value);
        }
        req.append("api_sig", sigBuilder.toSignature());
        for (FileData file : files) {
            req.append("photo", file.filename, file.contentType, file.data);
        }
        return req;
    }
}
