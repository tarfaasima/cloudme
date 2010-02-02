package org.cloudme.webgallery.service;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.ImageService;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.PhotoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoDataService {
	@Autowired
	private CacheService cacheService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private PhotoDataRepository photoDataRepository;

	public byte[] getPhotoData(final Long photoId, final ImageFormat format, final ContentType type) {
	    return cacheService.cachePhoto(photoId, format, type, new CacheProducer<byte[]>() {
	        public byte[] produce() {
				PhotoData photoData = photoDataRepository.find(photoId);
				byte[] input = photoData.getDataAsArray();
	            return imageService.process(input, format, type);
	        }
	    });
	}

	public void save(PhotoData photoData) {
		photoDataRepository.save(photoData);
	}
}
