package org.cloudme.webgallery.persistence.jdo;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.cloudme.webgallery.IdObject;
import org.cloudme.webgallery.persistence.Repository;
import org.datanucleus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractJdoRepository<K, T extends IdObject<K>> implements Repository<K, T> {
    private final Class<T> baseClass;
    private final String listOrder;
    private PersistenceManagerFactory pmf;

    public AbstractJdoRepository(Class<T> baseClass, String listOrder) {
        this.baseClass = baseClass;
        this.listOrder = listOrder;
    }

    @Autowired
    public void setPersistenceManagerFactory(PersistenceManagerFactory pmf) {
        this.pmf = pmf;
    }

    public void save(final T t) {
        PersistenceManager pm = getPersistenceManager();
        pm.makePersistent(t);
    }

    @SuppressWarnings("unchecked")
    public Collection<T> findAll() {
        PersistenceManager pm = getPersistenceManager();
        Query query = pm.newQuery(baseClass);
        if (!StringUtils.isWhitespace(listOrder)) {
            query.setOrdering(listOrder);
        }
        Collection<T> items = (Collection<T>) query.execute();
        pm.detachCopyAll(items);
        return items;
    }

    public T find(K id) {
        PersistenceManager pm = getPersistenceManager();
        return pm.getObjectById(baseClass, id);
    }

    public void delete(final K id) {
        PersistenceManager pm = getPersistenceManager();
        pm.deletePersistent(pm.getObjectById(baseClass, id));
    }

    protected PersistenceManager getPersistenceManager() {
        return pmf.getPersistenceManager();
    }
}
