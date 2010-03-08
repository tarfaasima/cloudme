package org.cloudme.webgallery.image;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicImageFormat extends SimpleImageFormat {
    private static final long serialVersionUID = -5058618548628028559L;
    private static final Pattern REGEX = Pattern.compile("(\\d*)x(\\d*)");

    public DynamicImageFormat(String id) {
        this(match(id));
    }

    private DynamicImageFormat(Matcher m) {
        super(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)), true);
    }

    private static Matcher match(String id) {
        Matcher m = REGEX.matcher(id);
        if (!m.find()) {
            throw new IllegalArgumentException(id);
        }
        return m;
    }
    
    @Override
    public String toString() {
        return getWidth() + "x" + getHeight();
    };
}
