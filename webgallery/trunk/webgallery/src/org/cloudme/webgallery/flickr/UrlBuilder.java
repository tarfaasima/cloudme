package org.cloudme.webgallery.flickr;


class UrlBuilder {
    private final StringBuilder builder;
    private boolean hasParams = false;

    public UrlBuilder(String url) {
        builder = new StringBuilder(url);
    }

    public void append(String name, String value) {
        if (value == null) {
            return;
        }
        if (hasParams) {
            builder.append("&");
        }
        else {
            builder.append("?");
        }
        builder.append(name).append("=").append(value);
        hasParams = true;
    }

    public String toUrl() {
        return builder.toString();
    }
}
