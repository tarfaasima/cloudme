package org.cloudme.webgallery.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class ConvertCollection<T, U> extends AbstractCollection<U> {
	private final Collection<T> collection;

	public ConvertCollection(Collection<T> collection) {
		this.collection = collection;
	}

	@Override
	public int size() {
		return collection.size();
	}

	@Override
	public Iterator<U> iterator() {
		return new Iterator<U>() {
			private final Iterator<T> iterator = collection.iterator();
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public U next() {
				return convert(iterator.next());
			}

			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}

	protected abstract U convert(T next);
}
