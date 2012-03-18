package org.cloudme.wrestle.convert;

public final class LongConverter implements Converter<Long> {
    @Override
    public Long convert(String str) {
        return Long.parseLong(str);
    }
}