package org.cloudme.sugar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

/**
 * An abstract DAO to handle Objectify persistence.
 * 
 * @author Moritz Petersen
 * 
 * @param <T>
 *            The entity type that is handled by this DAO.
 */
public abstract class AbstractDao<T> {
	private static Set<Class<?>> registeredBaseClasses = new HashSet<Class<?>>();

	/**
	 * Creates a {@link QueryOperator} to filter based on the given condition
	 * and value.
	 * 
	 * @param condition
	 *            The filter condition.
	 * @param value
	 *            The filter value.
	 * @return A {@link QueryOperator} to filter a query.
	 */
	protected static QueryOperator filter(final String condition, final Object value) {
		return new QueryOperator() {
			@Override
			public <T> Query<T> apply(Query<T> query) {
				return query.filter(condition, value);
			}

			@Override
			public String toString() {
				return "filter(" + condition + ", " + value + ")";
			}
		};
	}

	/**
	 * Creates a {@link QueryOperator} to sort a query result by the given
	 * condition.
	 * 
	 * @param condition
	 *            The sort condition.
	 * @return A {@link QueryOperator} to sort a query.
	 */
	protected static QueryOperator orderBy(final String condition) {
		return new QueryOperator() {
			@Override
			public <T> Query<T> apply(Query<T> query) {
				return query.order(condition);
			}

			@Override
			public String toString() {
				return "orderBy(" + condition + ")";
			}
		};
	}

	/**
	 * A callback class that can be applied to the
	 * {@link AbstractDao#execute(Callback)} method.
	 * 
	 * @author Moritz Petersen
	 * 
	 * @param <R>
	 *            Type of the result.
	 */
	protected abstract class Callback<R> {
		private final boolean needsTransaction;

		public Callback() {
			this(false);
		}

		/**
		 * Initializes the {@link Callback}.
		 * 
		 * @param needsTransaction
		 *            If <tt>true</tt> the {@link Callback} will be executed in
		 *            a transaction context.
		 */
		public Callback(boolean needsTransaction) {
			this.needsTransaction = needsTransaction;
		}

		/**
		 * To be implemented in order to execute operations on the
		 * {@link Objectify} instance.
		 * 
		 * @param ofy
		 *            The current {@link Objectify} instance.
		 * @return The result of the operations.
		 */
		protected abstract R execute(Objectify ofy);

		boolean isNeedsTransaction() {
			return needsTransaction;
		}
	}

	/**
	 * The base class of this DAO.
	 */
	protected final Class<T> baseClass;

	/**
	 * Initializes the DAO with the given class. The class will be registered in
	 * the {@link Objectify} context.
	 * 
	 * @param baseClass
	 *            The entity class.
	 */
	public AbstractDao(Class<T> baseClass) {
		this.baseClass = baseClass;
		register(baseClass);
	}

	/**
	 * Registers the base class to the {@link ObjectifyService}. This is done
	 * only once, thus all registered classes are tracked in a set.
	 * 
	 * @param baseClass
	 *            the {@link Class} to register.
	 */
	private static void register(Class<?> baseClass) {
		if (!registeredBaseClasses.contains(baseClass)) {
			ObjectifyService.register(baseClass);
			registeredBaseClasses.add(baseClass);
		}
	}

	/**
	 * Deletes an entity with the given id.
	 * 
	 * @param id
	 *            The id of the entity.
	 */
	public void delete(final Long id) {
		delete(new Key<T>(baseClass, id));
	}

	private void delete(final Key<T> key) {
		execute(new Callback<Object>(true) {
			@Override
			public Object execute(Objectify ofy) {
				ofy.delete(key);
				return null;
			}
		});
	}

	/**
	 * Delete all instances that are matched by the given operators.
	 * 
	 * @param operators
	 *            The operators to select the entities to be deleted.
	 */
	protected void deleteAll(QueryOperator... operators) {
		for (Key<T> key : ((Query<T>) findBy(operators)).fetchKeys()) {
			delete(key);
		}
	}

	/**
	 * Execute a {@link Callback} in a Objectify context.
	 * 
	 * @param <R>
	 *            The result type of the execution.
	 * @param callback
	 *            The {@link Callback}
	 * @return The result
	 */
	protected <R> R execute(Callback<R> callback) {
		Objectify ofy = callback.needsTransaction ? ObjectifyService.beginTransaction() : ObjectifyService.begin();
		Transaction txn = ofy.getTxn();
		try {
			R result = callback.execute(ofy);
			if (isActive(txn)) {
				txn.commit();
			}
			return result;
		} finally {
			if (isActive(txn)) {
				txn.rollback();
			}
		}
	}

	private boolean isActive(Transaction txn) {
		return txn != null && txn.isActive();
	}

	public T find(final Long id) {
		return execute(new Callback<T>() {
			@Override
			protected T execute(Objectify ofy) {
				return ofy.find(baseClass, id);
			}
		});
	}

	protected T findSingle(String condition, Object value) {
		return findSingle(filter(condition, value));
	}

	protected T findSingle(QueryOperator... operators) {
		Iterator<T> it = findBy(operators).iterator();
		T result = null;
		if (it.hasNext()) {
			result = it.next();
			if (it.hasNext()) {
				throw new IllegalStateException(String.format("Multiple objects found for class %s with %s",
						baseClass.getName(), Arrays.asList(operators)));
			}
		}
		return result;
	}

	public List<T> listAll() {
		return listBy();
	}

	protected List<T> listBy(final QueryOperator... operators) {
		return ((Query<T>) findBy(operators)).list();
	}

	public Iterable<T> findAll() {
		return findBy();
	}

	protected final Iterable<T> findBy(final QueryOperator... operators) {
		return execute(new Callback<Iterable<T>>() {
			@Override
			protected Iterable<T> execute(Objectify ofy) {
				Query<T> query = ofy.query(baseClass);
				for (QueryOperator operator : operators) {
					query = operator.apply(query);
				}
				return query;
			}
		});
	}

	public void put(final Iterable<? extends T> objs) {
		execute(new Callback<Object>() {
			@Override
			protected Object execute(Objectify ofy) {
				ofy.put(objs);
				return null;
			}
		});
	}

	public T put(final T t) {
		return execute(new Callback<T>(true) {
			@Override
			protected T execute(Objectify ofy) {
				ofy.put(t);
				return t;
			}
		});
	}
}
