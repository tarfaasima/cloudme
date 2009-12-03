package org.cloudme.webgallery;

import org.springframework.stereotype.Repository;

@Repository
public class JdoGalleryRepository extends AbstractJdoRepository<Gallery> implements GalleryRepository {
    public JdoGalleryRepository() {
        super(Gallery.class);
    }
}
