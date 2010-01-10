package org.cloudme.webgallery.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {
    public static <E> List<E> asList(Collection<E> c) {
        if (c instanceof List<?>) {
            return (List<E>) c;
        }
        return new ArrayList<E>(c);
    }
}
