package org.cloudme.webgallery.image;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicImageFormat extends SimpleImageFormat {
    private static final long serialVersionUID = -5058618548628028559L;
    private static final Pattern DEFAULT = Pattern.compile("(\\d*)x(\\d*)");
    private static final Pattern SCALED = Pattern.compile("(\\d*)#(\\d*)");
    
    public DynamicImageFormat(String id) {
        super(match(id));
    }

    private static ImageFormat match(String id) {
        Matcher m = DEFAULT.matcher(id);
        if (m.find()) {
            return createImageFormat(m, true);
        }
        m = SCALED.matcher(id);
        if (m.find()) {
            return createImageFormat(m, false);
        }
        throw new IllegalArgumentException(id);
    }

    private static SimpleImageFormat createImageFormat(Matcher m, boolean isCrop) {
        return new SimpleImageFormat(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)), isCrop);
    }
    
    @Override
    public String toString() {
        return getWidth() + "x" + getHeight() + (isCrop() ? "" : "scaled");
    };
}
