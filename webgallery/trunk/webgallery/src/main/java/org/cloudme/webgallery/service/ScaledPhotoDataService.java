package org.cloudme.webgallery.service;

import java.util.Collection;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.model.ScaledPhotoData;
import org.cloudme.webgallery.persistence.ScaledPhotoDataRepository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyScaledPhotoDataRepository;

public class ScaledPhotoDataService extends
        AbstractService<Long, ScaledPhotoData, ScaledPhotoDataRepository> {

    public ScaledPhotoDataService() {
        super(new ObjectifyScaledPhotoDataRepository());
    }

    public byte[] find(long photoId, ImageFormat format, ContentType type) {
        Collection<ScaledPhotoData> scaledPhotoDatas = repository.find(photoId,
                format.toString(),
                type
                .toString());
        if (scaledPhotoDatas.isEmpty()) {
            return null;
        }
        return scaledPhotoDatas.iterator().next().getDataAsArray();
    }

    public void deleteByPhotoId(long photoId) {
        repository.deleteByPhotoId(photoId);
    }
}
