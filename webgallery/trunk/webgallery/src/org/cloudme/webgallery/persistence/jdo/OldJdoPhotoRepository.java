package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.persistence.OldPhotoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OldJdoPhotoRepository extends AbstractJdoRepository<String, Photo> implements OldPhotoRepository {
    public OldJdoPhotoRepository() {
        super(Photo.class);
    }
}
