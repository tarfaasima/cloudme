package org.cloudme.webgallery.persistence.jdo;

import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;

import org.cloudme.webgallery.IdObject;
import org.cloudme.webgallery.persistence.Repository;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.orm.jdo.support.JdoDaoSupport;

public abstract class AbstractJdoRepository<K, T extends IdObject<K>> extends JdoDaoSupport implements Repository<K, T> {
	private JdoTemplate jdoTemplate;
	private final Class<T> baseClass;
	private final String listOrder;
	private PersistenceManager pm;

	public AbstractJdoRepository(Class<T> baseClass, String listOrder) {
		this.baseClass = baseClass;
		this.listOrder = listOrder;
	}

	//
	// @Autowired
	// public void init(PersistenceManagerFactory pmf) {
	// jdoTemplate = new JdoTemplate(pmf);
	// pm = pmf.getPersistenceManager();
	// }

	public void save(final T t) {
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistent(t);
		pm.close();
		// getJdoTemplate().makePersistent(t);
	}

	@SuppressWarnings("unchecked")
	public Collection<T> findAll() {
		return getJdoTemplate().execute(new JdoCallback<Collection<T>>() {
			@Override
			public Collection<T> doInJdo(PersistenceManager pm) throws JDOException {
				Collection<T> items = (Collection<T>) pm.newQuery(baseClass).execute();
				 items = pm.detachCopyAll(items);
				 return items;
			}
		});
	}

	public T find(K id) {
		return jdoTemplate.getObjectById(baseClass, id);
	}

	public void delete(final K id) {
		jdoTemplate.execute(new JdoCallback<T>() {
			public T doInJdo(PersistenceManager pm) throws JDOException {
				pm.deletePersistent(pm.getObjectById(baseClass, id));
				return null;
			}
		});
	}
}
