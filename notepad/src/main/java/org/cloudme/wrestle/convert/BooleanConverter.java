package org.cloudme.wrestle.convert;


public final class BooleanConverter implements Converter<Boolean> {
    @Override
    public Boolean convert(String str) {
        return Boolean.parseBoolean(str);
    }
}