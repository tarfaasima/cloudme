package org.cloudme.webgallery.image;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicImageFormat implements ImageFormat {
    private static final Pattern REGEX = Pattern.compile("(\\d*)x(\\d*)");
    private final int width;
    private final int height;

    public DynamicImageFormat(String id) {
        Matcher m = REGEX.matcher(id);
        if (!m.find()) {
            throw new IllegalArgumentException(id);
        }
        width = Integer.valueOf(m.group(1));
        height = Integer.valueOf(m.group(2));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isCrop() {
        return true;
    }
}
