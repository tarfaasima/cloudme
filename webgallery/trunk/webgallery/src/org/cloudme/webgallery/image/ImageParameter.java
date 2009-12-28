package org.cloudme.webgallery.image;

public enum ImageParameter {
    THUMBNAIL("t", 210), LARGE("l", 894);
    
    private final String id;
    private final int size;

    private ImageParameter(String id, int size) {
        this.id = id;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }
}
