package org.cloudme.webgallery.image;

public enum DefaultImageFormat implements ImageFormat {
    THUMBNAIL("t", 82, false), SMALL("s", 82, true), MEDIUM("m", 198, true), LARGE("l", 894, false);

    private final String id;
    private final boolean isCrop;
    private final int width;
    private final int height;

    private DefaultImageFormat(String id, int size, boolean isCrop) {
        this(id, size, size, isCrop);
    }

    private DefaultImageFormat(String id, int width, int height, boolean isCrop) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.isCrop = isCrop;
    }

    public String getId() {
        return id;
    }

    public boolean isCrop() {
        return isCrop;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
