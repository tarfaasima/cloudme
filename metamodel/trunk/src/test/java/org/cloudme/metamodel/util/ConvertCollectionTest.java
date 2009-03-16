package org.cloudme.metamodel.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;


public class ConvertCollectionTest {
    @Test
    public void testTransformedCollection() {
        List<String> strings = Arrays.asList("1", "2", "3");
        Collection<Integer> integers = new ConvertCollection<String, Integer>(strings) {
            @Override
            protected Integer convert(String s) {
                return Integer.valueOf(s);
            }
        };
        assertEquals(3, integers.size());
        Iterator<Integer> it = integers.iterator();
        int[] ints = {1, 2, 3};
        for (int i = 0; i < ints.length; i++) {
            assertTrue(ints[i] == it.next());
        }
    }
}
