package org.cloudme.webgallery;

import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

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
    
    public void save(Gallery gallery) {
        jdoTemplate.makePersistent(gallery);
        System.out.println("saved: " + gallery.getName());
    }
    
    @SuppressWarnings("unchecked")
    public Collection<Gallery> findAll() {
        return jdoTemplate.executeFind(new JdoCallback<Collection<Gallery>>() {
            public Collection<Gallery> doInJdo(PersistenceManager pm) throws JDOException {
                return pm.detachCopyAll((Collection<Gallery>) pm.newQuery(Gallery.class).execute());
            }
        });
    }
}
