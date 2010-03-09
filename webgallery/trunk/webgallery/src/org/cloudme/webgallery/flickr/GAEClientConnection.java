package org.cloudme.webgallery.flickr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

class GAEClientConnection implements ManagedClientConnection {

    public GAEClientConnection(ClientConnectionManager cm, HttpRoute route, Object state) {
        this.connManager = cm;
        this.route = route;
        this.state = state;
        this.closed = true;
    }

    // From interface ManagedClientConnection

    public boolean isSecure() {
        return route.isSecure();
    }

    public HttpRoute getRoute() {
        return route;
    }

    public javax.net.ssl.SSLSession getSSLSession() {
        return null;
    }

    public void open(HttpRoute route, HttpContext context, HttpParams params) throws IOException {
        close();
        this.route = route;
        // System.err.println(">>>>");
    }

    public void tunnelTarget(boolean secure, HttpParams params) throws IOException {
        throw new IOException("tunnelTarget() not supported");
    }

    public void tunnelProxy(HttpHost next, boolean secure, HttpParams params) throws IOException {
        throw new IOException("tunnelProxy() not supported");
    }

    public void layerProtocol(HttpContext context, HttpParams params) throws IOException {
        throw new IOException("layerProtocol() not supported");
    }

    public void markReusable() {
        reusable = true;
    }

    public void unmarkReusable() {
        reusable = false;
    }

    public boolean isMarkedReusable() {
        return reusable;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getState() {
        return state;
    }

    public void setIdleDuration(long duration, TimeUnit unit) {
        // Do nothing
    }

    // From interface HttpClientConnection

    public boolean isResponseAvailable(int timeout) throws IOException {
        return response != null;
    }

    public void sendRequestHeader(HttpRequest request) throws HttpException, IOException {
        try {
            HttpHost host = route.getTargetHost();

            URI uri = new URI(host.getSchemeName() + "://" + host.getHostName()
                    + ((host.getPort() == -1) ? "" : host.getPort()) + request.getRequestLine().getUri());

            this.request = new HTTPRequest(uri.toURL(), HTTPMethod.valueOf(request.getRequestLine().getMethod()),
                    FetchOptions.Builder.disallowTruncate().doNotFollowRedirects());
        } catch (URISyntaxException ex) {
            throw new IOException("Malformed request URI: " + ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            throw new IOException("Unsupported HTTP method: " + ex.getMessage(), ex);
        }

        // System.err.println("SEND: " + this.request.getMethod() + " " +
        // this.request.getURL());

        for (Header h : request.getAllHeaders()) {
            // System.err.println("SEND: " + h.getName() + ": " + h.getValue());
            this.request.addHeader(new HTTPHeader(h.getName(), h.getValue()));
        }
    }

    public void sendRequestEntity(HttpEntityEnclosingRequest request) throws HttpException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (request.getEntity() != null) {
            request.getEntity().writeTo(baos);
        }
        this.request.setPayload(baos.toByteArray());
    }

    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        if (this.response == null) {
            flush();
        }

        HttpResponse response = new BasicHttpResponse(new ProtocolVersion("HTTP", 1, 1), this.response
                .getResponseCode(), null);
        // System.err.println("RECV: " + response.getStatusLine());

        for (HTTPHeader h : this.response.getHeaders()) {
            // System.err.println("RECV: " + h.getName() + ": " + h.getValue());
            response.addHeader(h.getName(), h.getValue());
        }

        return response;
    }

    public void receiveResponseEntity(HttpResponse response) throws HttpException, IOException {
        if (this.response == null) {
            throw new IOException("receiveResponseEntity() called on closed connection");
        }

        ByteArrayEntity bae = new ByteArrayEntity(this.response.getContent());
        bae.setContentType(response.getFirstHeader("Content-Type"));
        response.setEntity(bae);

        response = null;
    }

    public void flush() throws IOException {
        if (request != null) {
            try {
                // System.err.println("----");
                response = urlFS.fetch(request);
                request = null;
            } catch (IOException ex) {
                ex.printStackTrace();
                throw ex;
            }
        } else {
            response = null;
        }
    }

    // From interface HttpConnection

    public void close() throws IOException {
        request = null;
        response = null;
        closed = true;
        // System.err.println("<<<<");
    }

    public boolean isOpen() {
        return request != null || response != null;
    }

    public boolean isStale() {
        return !isOpen() && !closed;
    }

    public void setSocketTimeout(int timeout) {
    }

    public int getSocketTimeout() {
        return -1;
    }

    public void shutdown() throws IOException {
        close();
    }

    public HttpConnectionMetrics getMetrics() {
        return null;
    }

    // From interface HttpInetConnection

    public InetAddress getLocalAddress() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

    public InetAddress getRemoteAddress() {
        return null;
    }

    public int getRemotePort() {
        HttpHost host = route.getTargetHost();
        return connManager.getSchemeRegistry().getScheme(host).resolvePort(host.getPort());
    }

    // From interface ConnectionReleaseTrigger

    public void releaseConnection() throws IOException {
        connManager.releaseConnection(this, Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    public void abortConnection() throws IOException {
        unmarkReusable();
        shutdown();
    }

    private ClientConnectionManager connManager;
    private HttpRoute route;
    private Object state;
    private boolean reusable;

    private HTTPRequest request;
    private HTTPResponse response;
    private boolean closed;

    private static URLFetchService urlFS = URLFetchServiceFactory.getURLFetchService();
}