package org.cloudme.webgallery.image;

public enum ContentType {
    JPEG("image/jpeg", "JPG", "JPEG"), PNG("image/png", "PNG");
    
    private final String type;
    private final String[] suffixes;

    private ContentType(String type, String... suffixes) {
        this.type = type;
        this.suffixes = suffixes;
    }
    
    @Override
    public String toString() {
        return type;
    }

    public String[] getSuffixes() {
        return suffixes;
    }
}
