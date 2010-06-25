package org.cloudme.webgallery.service;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.cache.gae.GaeCacheService;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.DefaultImageFormat;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.ImageService;
import org.cloudme.webgallery.image.gae.GaeImageService;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.model.ScaledPhotoData;
import org.cloudme.webgallery.persistence.PhotoDataRepository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyPhotoDataRepository;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;

public class PhotoDataService extends
        AbstractService<Long, PhotoData, PhotoDataRepository> {
    private final CacheService cacheService = new GaeCacheService();
    private final ImageService imageService = new GaeImageService();
    private final PhotoService photoService = new PhotoService();
    private final ScaledPhotoDataService scaledPhotoDataService = new ScaledPhotoDataService();
    private final PhotoDataRepository photoDataRepository = new ObjectifyPhotoDataRepository();

    protected PhotoDataService() {
        super(new ObjectifyPhotoDataRepository());
    }

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
     * @return The scaled image data, or <code>null</code> if any error occured.
     */
    public byte[] getPhotoData(final Long photoId,
            final ImageFormat format,
            final ContentType type) {
        return cacheService.cache(new CacheProducer<byte[]>() {
            public byte[] produce() {
                if (format instanceof DefaultImageFormat) {
                    byte[] data = scaledPhotoDataService.find(photoId,
                            format,
                            type);
                    if (data != null) {
                        return data;
                    }
                    addToQueue(photoId);
                }
                float balance = photoService.find(photoId).getCropBalance();
                PhotoData photoData = photoDataRepository.find(photoId);
                byte[] input = photoData.getDataAsArray();
                return imageService.process(input, format, type, balance);
            }
        }, photoId, format, type);
    }

    @Override
    public void save(PhotoData photoData) {
        photoDataRepository.save(photoData);
        addToQueue(photoData.getId());
    }

    private void addToQueue(Long id) {
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder.url("/organize/generate/" + id));
    }

    @Override
    public void delete(Long id) {
        photoDataRepository.delete(id);
    }

    public void generate(long photoId, ContentType type) {
        DefaultImageFormat[] formats = DefaultImageFormat.values();
        float balance = photoService.find(photoId).getCropBalance();
        byte[] input = photoDataRepository.find(photoId).getDataAsArray();
        for (ImageFormat format : formats) {
            byte[] output = imageService.process(input, format, type, balance);
            ScaledPhotoData scaledPhotoData = new ScaledPhotoData();
            scaledPhotoData.setDataAsArray(output);
            scaledPhotoData.setFormat(format.toString());
            scaledPhotoData.setPhotoId(photoId);
            scaledPhotoData.setType(type.toString());
            scaledPhotoDataService.save(scaledPhotoData);
        }
    }
}
