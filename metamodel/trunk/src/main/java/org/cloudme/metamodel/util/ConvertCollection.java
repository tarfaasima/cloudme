package org.cloudme.metamodel.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * An immutable collection that transforms the elements of the input collection
 * to a different type. The {@link #convert(Object)} method needs to be
 * overwritten. This collection allows a type conversion between collections.
 * 
 * @author Moritz Petersen
 * 
 * @param <T>
 *            The input type.
 * @param <E>
 *            The type of the resulting collection.
 */
public abstract class ConvertCollection<T, E> implements Collection<E> {
    private final Collection<T> col;

    /**
     * Creates a new {@link ConvertCollection} from the given collection.
     * 
     * @param col
     */
    public ConvertCollection(Collection<T> col) {
        this.col = col;
    }

    /**
     * Not implemented.
     */
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented.
     */
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented.
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented.
     */
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented.
     */
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return col.isEmpty();
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Iterator<T> it = col.iterator();

            public boolean hasNext() {
                return it.hasNext();
            }

            public E next() {
                return convert(it.next());
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Needs to be overwritten to perform conversion between the input
     * collection's types and the required type off the output collection.
     * 
     * @param t
     *            The input type.
     * @return The converted type.
     */
    protected abstract E convert(T t);

    /**
     * Not implemented.
     */
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented.
     */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented.
     */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return col.size();
    }

    /**
     * Not implemented.
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented.
     */
    @SuppressWarnings("hiding")
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
}
