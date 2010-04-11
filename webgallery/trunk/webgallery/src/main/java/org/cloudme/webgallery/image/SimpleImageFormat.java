package org.cloudme.webgallery.image;

public class SimpleImageFormat implements ImageFormat {
    private static final long serialVersionUID = 3416317490232456927L;
    private final int width;
    private final int height;
    private final boolean isCrop;
    
    public SimpleImageFormat(ImageFormat format) {
        this(format.getWidth(), format.getHeight(), format.isCrop());
    }

    public SimpleImageFormat(int width, int height, boolean isCrop) {
        this.width = width;
        this.height = height;
        this.isCrop = isCrop;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isCrop() {
        return isCrop;
    }
}
