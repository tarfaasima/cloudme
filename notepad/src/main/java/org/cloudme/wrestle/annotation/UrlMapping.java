package org.cloudme.wrestle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cloudme.wrestle.ActionHandler;
import org.cloudme.wrestle.WrestleController;

/**
 * Provides (partial) URL mapping to a class. Can be used in both,
 * {@link WrestleController} implementations or {@link ActionHandler} classes.
 * The full URL mapping will be concatenated of all involved classes.
 * <p>
 * If the controller has the mapping "api" and the action handler has the
 * mapping "account" then it will be combined to "/api/account".
 * 
 * @See {@link WrestleController} {@link ActionHandler}
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface UrlMapping {
    static class Util {
        /**
         * Returns the value of this annotation of the given Object. If the
         * annotation is not present, it returns <code>null</code>. Otherwise
         * the value will be normalized to start with a '/' and <em>not</em> to
         * end with a '/'.
         * 
         * @param obj
         *            The object whose annotation value will be returned.
         * @return The normalized value (with leading '/' but without trailing
         *         '/').
         */
        public static String normalizedValue(Object obj) {
            UrlMapping annotation = obj.getClass().getAnnotation(UrlMapping.class);
            if (annotation == null) {
                return "";
            }
            String urlMapping = annotation.value();
            if (urlMapping == null) {
                return "";
            }
            if (urlMapping.charAt(0) != '/') {
                urlMapping = '/' + urlMapping;
            }
            if (urlMapping.charAt(urlMapping.length() - 1) == '/') {
                urlMapping = urlMapping.substring(0, urlMapping.length() - 1);
            }
            return urlMapping;
        }
    }

    String value();
}
