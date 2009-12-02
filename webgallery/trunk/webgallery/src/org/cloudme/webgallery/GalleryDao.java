package org.cloudme.webgallery;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Repository;

@Repository
public class GalleryDao {
//    private final JdoTemplate jdoTemplate;

//    @Autowired
//    public GalleryDao(PersistenceManagerFactory pmf) {
//        jdoTemplate = new JdoTemplate(pmf);
//    }
    
//    @Transactional
    public void save(Gallery gallery) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(gallery);
        }
        finally {
            pm.close();
        }
//        jdoTemplate.makePersistent(gallery);
        System.out.println("saved: " + gallery.getName());
    }
    
    @SuppressWarnings("unchecked")
    public Collection<Gallery> findAll() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            Query query = pm.newQuery(Gallery.class);
            Collection<Gallery> galleries = (Collection<Gallery>) query.execute();
            return pm.detachCopyAll(galleries);
        }
        finally {
            pm.close();
        }
//        return jdoTemplate.executeFind(new JdoCallback<Collection<Gallery>>() {
//            public Collection<Gallery> doInJdo(PersistenceManager pm) throws JDOException {
//                Set galleries = pm.getManagedObjects(Gallery.class);
//                if (galleries == null) {
//                    return Collections.EMPTY_LIST;
//                }
//                return pm.detachCopyAll(galleries);
//            }
//        });
    }
}
