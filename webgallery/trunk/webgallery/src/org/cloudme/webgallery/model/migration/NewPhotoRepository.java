package org.cloudme.webgallery.model.migration;

import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.jdo.AbstractJdoRepository;

public class NewPhotoRepository extends AbstractJdoRepository<Long, Photo> {
    public NewPhotoRepository() {
        super(Photo.class);
    }
}
