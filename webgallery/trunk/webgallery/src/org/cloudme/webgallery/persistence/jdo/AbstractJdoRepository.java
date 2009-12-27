package org.cloudme.webgallery.persistence.jdo;

import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.cloudme.webgallery.IdObject;
import org.cloudme.webgallery.persistence.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.support.JdoDaoSupport;

public abstract class AbstractJdoRepository<K, T extends IdObject<K>> extends JdoDaoSupport implements Repository<K, T> {
    private final Class<T> baseClass;

    public AbstractJdoRepository(Class<T> baseClass) {
        this.baseClass = baseClass;
    }
    
    @Autowired
    public void init(PersistenceManagerFactory pmf) {
        setPersistenceManagerFactory(pmf);
    }

    public void save(final T t) {
        getJdoTemplate().makePersistent(t);
    }

    @SuppressWarnings("unchecked")
    public Collection<T> findAll() {
        return getJdoTemplate().executeFind(new JdoCallback<Collection<T>>() {
            public Collection<T> doInJdo(PersistenceManager pm) throws JDOException {
                Query query = pm.newQuery(baseClass);
                Collection<T> items = (Collection<T>) query.execute();
                pm.retrieveAll(items);
                pm.makeTransientAll(items);
                return items;
            }
        });
    }

    public T find(final K id) {
        return getJdoTemplate().execute(new JdoCallback<T>() {
            public T doInJdo(PersistenceManager pm) throws JDOException {
                T item = pm.getObjectById(baseClass, id);
                pm.retrieveAll(item);
                pm.makeTransientAll(item);
                return item;
            }
        });
    }

    public void delete(final K id) {
        getJdoTemplate().execute(new JdoCallback<T>() {
            public T doInJdo(PersistenceManager pm) throws JDOException {
                T item = pm.getObjectById(baseClass, id);
                pm.deletePersistent(item);
                return null;
            }
        });
    }
}
