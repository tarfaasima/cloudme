package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.Gallery;
import org.springframework.stereotype.Repository;

@Repository
public class JdoGalleryRepository extends AbstractJdoRepository<Gallery> {
    public JdoGalleryRepository() {
        super(Gallery.class, "name asc");
    }
}
