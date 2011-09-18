package org.cloudme.passwolk.util;

public class StringUtils {

    /**
     * Convenience method to test if a {@link String} is <code>null</code> or
     * does not contain any characters. Does not consider trailing spaces.
     * 
     * @param str
     *            The tested {@link String}.
     * @return <code>true</code> if the {@link String} is <code>null</code> or
     *         does not contain any characters. Otherwise <code>false</code>.
     */
    public static boolean isEmpty(String str) {
    	return str == null || str.length() == 0;
    }

}
