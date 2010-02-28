package org.cloudme.webgallery.flickr;

class UrlBuilder {
    private final StringBuilder builder;
    private boolean hasParams = false;
    private final boolean isPost;

    public UrlBuilder(String url, boolean isPost) {
        this.isPost = isPost;
        builder = new StringBuilder(url);
    }

    public void append(String name, String value) {
        if (value == null || isPost) {
            return;
        }
        if (builder != null) {
            if (hasParams) {
                builder.append("&");
            }
            else {
                builder.append("?");
            }
            builder.append(name).append("=").append(value);
            hasParams = true;
        }
    }

    public String toUrl() {
        return builder.toString();
    }
}
