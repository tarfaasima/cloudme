package org.cloudme.wrestle.convert;


public final class FloatConverter implements Converter<Float> {
    @Override
    public Float convert(String str) {
        return Float.parseFloat(str);
    }
}