package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.model.PhotoData;
import org.springframework.stereotype.Repository;

@Repository
public class JdoPhotoDataRepository extends AbstractJdoRepository<Long, PhotoData> {
    public JdoPhotoDataRepository() {
        super(PhotoData.class);
    }
}
