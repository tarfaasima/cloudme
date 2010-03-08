package org.cloudme.webgallery.flickr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;

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

    private final SortedMap<String, String> params = new TreeMap<String, String>();
    private final Collection<FileData> files = new ArrayList<FileData>();
    private String secret;
    private String url;

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
        return value.toString();
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
        this.url = url.toString();
    }

    /**
     * Executes the request and returns the response.
     * 
     * @return the response.
     */
    public FlickrResponse execute() {
        HttpUriRequest request = getHttpRequest();
        SchemeRegistry schemeRegistry = new SchemeRegistry(); 
        schemeRegistry.register( 
          new Scheme("http", PlainSocketFactory.getSocketFactory(), 80)); 
        BasicHttpParams params = new BasicHttpParams(); 
        SingleClientConnManager connmgr = 
          new SingleClientConnManager(params, schemeRegistry); 
        HttpClient client = new DefaultHttpClient(connmgr, params);
        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            return new FlickrResponse(entity.getContent());
        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Constructs the HTTP request.
     * 
     * @return The HTTP request.
     */
    public HttpUriRequest getHttpRequest() {
        HttpHandler handler = files.isEmpty() ? new HttpGetHandler(url) : new HttpPostHandler(url);
        SignatureBuilder sigBuilder = new SignatureBuilder(secret);
        for (Entry<String, String> param : params.entrySet()) {
            String name = param.getKey();
            String value = param.getValue();
            handler.append(name, value);
            sigBuilder.append(name, value);
        }
        handler.append("api_sig", sigBuilder.toSignature());
        for (FileData file : files) {
            handler.append("photo", file.filename, file.contentType, file.data);
        }
        return handler.getRequest();
    }
}
