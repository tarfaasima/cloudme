package org.cloudme.webgallery.service;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.ImageService;
import org.cloudme.webgallery.model.Photo;
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

	/**
	 * Returns the {@link Photo}'s image data for the given {@link Photo} ID,
	 * scaled to the given {@link ImageFormat} and with the given
	 * {@link ContentType}. It uses the {@link CacheService} to cache the data.
	 * For scaling the {@link ImageService} is used.
	 * 
	 * @param photoId
	 *            The ID of th {@link Photo}.
	 * @param format
	 *            The {@link ImageFormat} - dimensions of the scaled photo.
	 * @param type
	 *            The {@link ContentType} of the image data.
	 * @return The scaled image data.
	 */
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

	public void delete(Long id) {
		photoDataRepository.delete(id);
	}
}
