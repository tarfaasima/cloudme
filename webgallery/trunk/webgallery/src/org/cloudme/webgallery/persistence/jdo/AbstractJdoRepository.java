package org.cloudme.webgallery.persistence.jdo;

import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.cloudme.webgallery.IdObject;
import org.cloudme.webgallery.persistence.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;

public abstract class AbstractJdoRepository<K, T extends IdObject<K>> implements Repository<K, T> {
    private JdoTemplate jdoTemplate;
    private final Class<T> baseClass;
    private final String listOrder;

    public AbstractJdoRepository(Class<T> baseClass, String listOrder) {
        this.baseClass = baseClass;
        this.listOrder = listOrder;
    }

    @Autowired
    public void init(PersistenceManagerFactory pmf) {
        jdoTemplate = new JdoTemplate(pmf);
    }

    public void save(T t) {
        jdoTemplate.makePersistent(t);
    }

    @SuppressWarnings("unchecked")
    public Collection<T> findAll() {
        PersistenceManager pm = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager();
        Collection<T> items = (Collection<T>) pm.newQuery(baseClass).execute();
        items = pm.detachCopyAll(items);
        pm.close();
        return items;
//        return jdoTemplate.execute(new JdoCallback<Collection<T>>() {
//            @SuppressWarnings("unchecked")
//            public Collection<T> doInJdo(PersistenceManager pm) throws JDOException {
//                Query query = pm.newQuery(baseClass);
//                if (listOrder != null && listOrder.trim().length() > 0) {
//                    query.setOrdering(listOrder);
//                }
//                Collection<T> items = (Collection<T>) query.execute();
////                System.out.println(items);
//                return pm.detachCopyAll(items);
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
