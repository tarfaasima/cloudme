package org.cloudme.webgallery.tags;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Functions {
    public static String coyprightYear(String year, String separator) {
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());
        return year + (currentYear.equals(year) ? "" : separator + currentYear);
    }
}
