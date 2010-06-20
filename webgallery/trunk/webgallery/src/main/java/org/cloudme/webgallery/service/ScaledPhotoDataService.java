package org.cloudme.webgallery.service;

import java.util.Collection;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.model.ScaledPhotoData;
import org.cloudme.webgallery.persistence.ScaledPhotoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScaledPhotoDataService extends
        AbstractService<Long, ScaledPhotoData> {
    private final ScaledPhotoDataRepository scaledPhotoDataRepository;

    @Autowired
    public ScaledPhotoDataService(ScaledPhotoDataRepository scaledPhotoDataRepository) {
        super(scaledPhotoDataRepository);
        this.scaledPhotoDataRepository = scaledPhotoDataRepository;
    }

    public byte[] find(long photoId, ImageFormat format, ContentType type) {
        Collection<ScaledPhotoData> scaledPhotoDatas = scaledPhotoDataRepository.find(photoId, format.toString(), type
                .toString());
        if (scaledPhotoDatas.isEmpty()) {
            return null;
        }
        return scaledPhotoDatas.iterator().next().getDataAsArray();
    }

    public void deleteByPhotoId(long photoId) {
        scaledPhotoDataRepository.deleteByPhotoId(photoId);
    }
}
