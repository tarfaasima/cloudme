package org.cloudme.wrestle.convert;

public interface Converter<T> {
    T convert(String str);
}
