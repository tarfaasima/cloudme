package org.cloudme.mediacopy;

public class Strings {
    /**
     * Merges an array of {@link String}s to one {@link String}.
     * 
     * @param strings
     *            The array of {@link String}s.
     * @param fill
     *            The {@link String} that is used to fill between the parts of
     *            the array.
     * @param startIndex
     *            The starting index of the array.
     * @param endIndex
     *            The ending index of the array.
     * @return The merged array.
     */
    public static String merge(String[] strings, String fill, int startIndex, int endIndex) {
        StringBuffer sb = new StringBuffer();
        if (startIndex < endIndex) {
            for (int i = startIndex; i <= endIndex; i++) {
                if (sb.length() > 0) {
                    sb.append(fill);
                }
                sb.append(strings[i]);
            }
        }
        else {
            for (int i = startIndex; i >= endIndex; i--) {
                if (sb.length() > 0) {
                    sb.append(fill);
                }
                sb.append(strings[i]);
            }
        }
        return sb.toString();
    }
}
