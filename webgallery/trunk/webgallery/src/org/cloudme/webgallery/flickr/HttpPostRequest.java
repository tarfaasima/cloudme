package org.cloudme.webgallery.flickr;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

class HttpPostRequest extends AbstractHttpRequest {
    private static final String BOUNDARY_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String NL = "\r\n";
    private final String url;
    private int contentLength;
    private final String boundary;
    private final String host;
    private final String path;
    private final StringBuilder content = new StringBuilder();

    public HttpPostRequest(String url) {
        this.url = url;
        try {
            URL u = new URL(url);
            host = u.getHost();
            path = u.getPath();
        }
        catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
        boundary = createBoundary();
    }

    private String createBoundary() {
        Random rand = new Random();
        int length = 30 + rand.nextInt(10);
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = BOUNDARY_CHARS.charAt(rand.nextInt(length));
        }
        return new String(chars);
    }

    @Override
    void append(String name, String value) {
        content.append("Content-Disposition: form-data; name=\"").append(name).append("\"").append(NL);
        content.append(NL);
        content.append(value).append(NL);
        content.append(boundary).append(NL);
    }

    @Override
    public void writeTo(URLConnection con) {
        con.setDoInput(false);
        PrintWriter out = null;
        try {
            out = new PrintWriter(con.getOutputStream());
            out.println("POST " + path + " HTTP/1.1");
            out.println("Content-Type: multipart/form-data); boundary=" + boundary);
            out.println("Host: " + host);
            out.println("Content-Length: " + contentLength);
            out.println();
            out.println(boundary);
            out.print(content.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    void append(String name, String filename, String contentType, byte[] data) {
        contentLength += data.length;
        content.append("Content-Disposition: form-data; name=\"").append(name).append("\"; filename=\"").append(filename).append("\"").append(NL);
        content.append("Content-Type: ").append(contentType).append(NL);
        content.append(NL);
        content.append(data).append(NL);
        content.append(boundary).append(NL);
    }

    @Override
    public String getUrl() {
        return url;
    }
}
