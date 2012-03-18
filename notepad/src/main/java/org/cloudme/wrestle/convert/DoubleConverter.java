package org.cloudme.wrestle.convert;


public final class DoubleConverter implements Converter<Double> {
    @Override
    public Double convert(String str) {
        return Double.parseDouble(str);
    }
}