package org.cloudme.webgallery;

import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;

public abstract class AbstractJdoRepository<T> implements Repository<T> {
    private JdoTemplate jdoTemplate;
    private final Class<T> baseClass;

    public AbstractJdoRepository(Class<T> baseClass) {
        this.baseClass = baseClass;
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
                return pm.detachCopyAll((Collection<T>) pm.newQuery(baseClass).execute());
            }
        });
    }

    public T find(final long id) {
        return jdoTemplate.getObjectById(baseClass, id);
    }
}
