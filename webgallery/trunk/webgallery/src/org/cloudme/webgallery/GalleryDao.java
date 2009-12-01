package org.cloudme.webgallery;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GalleryDao {
    private final JdoTemplate jdoTemplate;

    @Autowired
    public GalleryDao(PersistenceManagerFactory pmf) {
        jdoTemplate = new JdoTemplate(pmf);
    }
    
    @Transactional
    public void save(Gallery gallery) {
        jdoTemplate.makePersistent(gallery);
    }
    
    @SuppressWarnings("unchecked")
    public Collection<Gallery> findAll() {
        return jdoTemplate.executeFind(new JdoCallback<Collection<Gallery>>() {
            public Collection<Gallery> doInJdo(PersistenceManager pm) throws JDOException {
                Set galleries = pm.getManagedObjects(Gallery.class);
                if (galleries == null) {
                    return Collections.EMPTY_LIST;
                }
                return pm.detachCopyAll(galleries);
            }
        });
    }
}
