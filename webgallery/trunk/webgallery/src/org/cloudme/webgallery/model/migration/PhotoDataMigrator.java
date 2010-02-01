package org.cloudme.webgallery.model.migration;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoDataRepository;

public class PhotoDataMigrator {
    public void migrate(StringBuilder log, JdoPhotoDataRepository jdoPhotoDataRepository, Long id, byte[] data) {
        PhotoData photoData = new PhotoData();
        photoData.setId(id);
        photoData.setDataAsArray(data);
        jdoPhotoDataRepository.save(photoData);
        log.append(new ToStringBuilder(photoData).append(id).toString() + "\n");
    }
}
