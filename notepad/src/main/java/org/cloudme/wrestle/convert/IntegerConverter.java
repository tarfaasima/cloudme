package org.cloudme.wrestle.convert;

public final class IntegerConverter implements Converter<Integer> {
    @Override
    public Integer convert(String str) {
        return Integer.parseInt(str);
    }
}