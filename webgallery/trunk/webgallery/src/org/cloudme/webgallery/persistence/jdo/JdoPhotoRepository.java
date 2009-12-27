package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.Photo;
import org.springframework.stereotype.Repository;

@Repository
public class JdoPhotoRepository extends AbstractJdoRepository<String, Photo> {
    public JdoPhotoRepository() {
        super(Photo.class);
    }
}
