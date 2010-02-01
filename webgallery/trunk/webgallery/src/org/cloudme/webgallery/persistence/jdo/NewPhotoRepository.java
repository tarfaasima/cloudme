package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.model.Photo;
import org.springframework.stereotype.Repository;

@Repository
public class NewPhotoRepository extends AbstractJdoRepository<Long, Photo> {
    public NewPhotoRepository() {
        super(Photo.class);
    }
}
