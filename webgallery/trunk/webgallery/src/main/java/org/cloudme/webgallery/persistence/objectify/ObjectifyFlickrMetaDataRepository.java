package org.cloudme.webgallery.persistence.objectify;

import org.cloudme.webgallery.model.FlickrMetaData;
import org.cloudme.webgallery.persistence.FlickrMetaDataRepository;

public class ObjectifyFlickrMetaDataRepository extends
        BaseObjectifyRepository<FlickrMetaData> implements
        FlickrMetaDataRepository {
    public ObjectifyFlickrMetaDataRepository() {
        super(FlickrMetaData.class);
    }
}
