package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.PhotoDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JdoPhotoDataRepository extends AbstractJdoRepository<Long, PhotoData> implements PhotoDataRepository {
    public JdoPhotoDataRepository() {
        super(PhotoData.class);
    }
}
