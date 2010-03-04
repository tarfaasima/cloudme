package org.cloudme.webgallery.flickr;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import org.apache.commons.io.output.ByteArrayOutputStream;

class HttpPostRequest implements AppendableHttpRequest {
    private static final String BOUNDARY_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String NL = "\r\n";
    private final String url;
    private final String boundary;
    private final String host;
    private final String path;
    private final ByteArrayOutputStream content = new ByteArrayOutputStream();
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
        }
        catch (MalformedURLException e) {
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
        PrintStream out = null;
        try {
            out = new PrintStream(con.getOutputStream()) {
                @Override
                public void print(String s) {
                    super.print(s);
                    System.out.print(s);
                }
            };
            out.print("POST " + path + " HTTP/1.1" + NL);
            out.print("Content-Type: multipart/form-data; boundary=" + boundary + NL);
            out.print("Host: " + host + NL);
            out.print("Content-Length: " + contentLength + NL);
            if (content.size() > 0) {
                out.print(NL);
                out.print(boundary + NL);
                out.print(content.toByteArray());
            }
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

    public void append(String name, String filename, String contentType, byte[] data) {
        contentLength += data.length;
        addContent("Content-Disposition: form-data; name=\"", name, "\"; filename=\"", filename, "\"");
        addContent("Content-Type: ", contentType);
        addContent();
        addContent(data);
        addContent(boundary);
    }

    public String getUrl() {
        return url;
    }

    private void addContent(Object... args) {
        try {
            for (Object arg : args) {
                if (arg != null) {
                    if (arg instanceof byte[]) {
                        content.write((byte[]) arg);
                    }
                    content.write(arg.toString().getBytes());
                }
            }
            content.write(NL.getBytes());
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
