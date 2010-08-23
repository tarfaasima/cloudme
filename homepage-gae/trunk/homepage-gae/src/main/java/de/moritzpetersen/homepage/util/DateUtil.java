package de.moritzpetersen.homepage.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtil {
    public static DateFormat getDateTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
