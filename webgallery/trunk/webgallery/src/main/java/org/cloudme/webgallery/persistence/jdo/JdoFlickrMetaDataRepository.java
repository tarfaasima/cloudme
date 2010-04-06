package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.model.FlickrMetaData;
import org.cloudme.webgallery.persistence.FlickrMetaDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JdoFlickrMetaDataRepository extends AbstractJdoRepository<Long, FlickrMetaData> implements FlickrMetaDataRepository {
    public JdoFlickrMetaDataRepository() {
        super(FlickrMetaData.class);
	}
}
