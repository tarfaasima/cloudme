package org.cloudme.webgallery.persistence.jdo;

import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.cloudme.webgallery.IdObject;
import org.cloudme.webgallery.persistence.Repository;
import org.datanucleus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;

public abstract class AbstractJdoRepository<K, T extends IdObject<K>> implements Repository<K, T> {
    private JdoTemplate jdoTemplate;
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
        pmf.getPersistenceManager().makePersistent(t);
//        getJdoTemplate().makePersistent(t);
    }

    @SuppressWarnings("unchecked")
    public Collection<T> findAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
//        return getJdoTemplate().executeFind(new JdoCallback<Collection<T>>() {
//            public Collection<T> doInJdo(PersistenceManager pm) throws JDOException {
                Query query = pm.newQuery(baseClass);
                if (!StringUtils.isWhitespace(listOrder)) {
                    query.setOrdering(listOrder);
                }
                Collection<T> items = (Collection<T>) query.execute();
                pm.detachCopyAll(items);
                return items;
//            }
//        });
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
