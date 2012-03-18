package org.cloudme.wrestle.convert;

import java.text.DateFormat;
import java.util.Date;

import lombok.SneakyThrows;


public final class DateConverter implements Converter<Date> {
    @Override
    @SneakyThrows
    public Date convert(String str) {
        return DateFormat.getDateInstance().parse(str);
    }
}