package org.cloudme.webgallery.flickr;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

class HttpPostRequest implements AppendableHttpRequest {
    private static final String BOUNDARY_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String NL = "\r\n";
    private final String url;
    private final String boundary;
    private final String host;
    private final String path;
    private final StringBuilder content = new StringBuilder();
    private int contentLength;

    public HttpPostRequest(String url) {
        this(url, createBoundary());
    }

    HttpPostRequest(String url, String boundary) {
        this.url = url;
        this.boundary = boundary;
        try {
            URL u = new URL(url);
            host = u.getHost();
            path = u.getPath();
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String createBoundary() {
        Random rand = new Random();
        int length = 30 + rand.nextInt(10);
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = BOUNDARY_CHARS.charAt(rand.nextInt(length));
        }
        return new String(chars);
    }

    public void append(String name, String value) {
        addContent("Content-Disposition: form-data; name=\"", name, "\"");
        addContent();
        addContent(value);
        addContent(boundary);
    }

    public void writeTo(URLConnection con) {
        con.setDoOutput(true);
        PrintWriter out = null;
        try {
            out = new PrintWriter(con.getOutputStream());
            out.println("POST " + path + " HTTP/1.1");
            out.println("Content-Type: multipart/form-data; boundary=" + boundary);
            out.println("Host: " + host);
            out.println("Content-Length: " + contentLength);
            if (content.length() > 0) {
                out.println();
                out.println(boundary);
                out.print(content.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public void append(String name, String filename, String contentType, byte[] data) {
        contentLength += data.length;
        addContent("Content-Disposition: form-data; name=\"", name, "\"; filename=\"", filename, "\"");
        addContent("Content-Type: ", contentType);
        addContent();
        addContent(new String(data));
        addContent(boundary);
    }

    public String getUrl() {
        return url;
    }
    
    private void addContent(Object... args) {
        for (Object arg : args) {
            if (arg != null) {
                content.append(arg.toString());
            }
        }
        content.append(NL);
    }
}
