package org.cloudme.webgallery.model.migration;

import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.jdo.AbstractJdoRepository;

public class NewPhotoDataRepository extends AbstractJdoRepository<Long, PhotoData> {
    public NewPhotoDataRepository() {
        super(PhotoData.class);
    }
}
