package org.cloudme.webgallery.persistence.jdo;

import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.cloudme.webgallery.persistence.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;

public abstract class AbstractJdoRepository<T> implements Repository<T> {
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
    
    public Collection<T> findAll() {
        return jdoTemplate.execute(new JdoCallback<Collection<T>>() {
            @SuppressWarnings("unchecked")
            public Collection<T> doInJdo(PersistenceManager pm) throws JDOException {
                Query query = pm.newQuery(baseClass);
                query.setOrdering(listOrder);
                return pm.detachCopyAll((Collection<T>) query.execute());
            }
        });
    }

    public T find(String id) {
        return jdoTemplate.getObjectById(baseClass, id);
    }
    
    public void delete(final String id) {
        jdoTemplate.execute(new JdoCallback<T>() {
            public T doInJdo(PersistenceManager pm) throws JDOException {
                pm.deletePersistent(pm.getObjectById(baseClass, id));
                return null;
            }
        });
    }
}
