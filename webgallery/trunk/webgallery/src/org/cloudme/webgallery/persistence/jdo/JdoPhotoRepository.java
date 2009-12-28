package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.persistence.PhotoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JdoPhotoRepository extends AbstractJdoRepository<String, Photo> implements PhotoRepository {
    public JdoPhotoRepository() {
        super(Photo.class);
    }
}
