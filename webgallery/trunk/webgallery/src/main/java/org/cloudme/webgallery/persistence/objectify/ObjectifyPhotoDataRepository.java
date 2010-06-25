package org.cloudme.webgallery.persistence.objectify;

import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.PhotoDataRepository;

public class ObjectifyPhotoDataRepository extends
        BaseObjectifyRepository<PhotoData>
        implements PhotoDataRepository {

    public ObjectifyPhotoDataRepository() {
        super(PhotoData.class);
    }
}
