package org.cloudme.webgallery.image;

public enum ImageFormat {
    THUMBNAIL("t", 210, true), LARGE("l", 894, false);
    
    private final String id;
    private final int size;
    private final boolean isSquare;

    private ImageFormat(String id, int size, boolean isSquare) {
        this.id = id;
        this.size = size;
        this.isSquare = isSquare;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public boolean isSquare() {
        return isSquare;
    }
}
