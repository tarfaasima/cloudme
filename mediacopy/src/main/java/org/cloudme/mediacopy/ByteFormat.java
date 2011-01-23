package org.cloudme.mediacopy;

public class ByteFormat {
    public static String formatMB(long val) {
        return ((val >> 10) >> 10) + "MB";
    }
}
