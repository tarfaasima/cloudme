package org.cloudme.webgallery.service;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.DefaultImageFormat;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.ImageService;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.model.ScaledPhotoData;
import org.cloudme.webgallery.persistence.PhotoDataRepository;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.inject.Inject;

public class PhotoDataService extends
        AbstractService<Long, PhotoData, PhotoDataRepository> {
    public interface PostProcessor {
        void process(ImageFormat format, byte[] output);
    }

    @Inject
    private CacheService cacheService;
    @Inject
    private ImageService imageService;
    @Inject
    private PhotoService photoService;
    @Inject
    private ScaledPhotoDataService scaledPhotoDataService;
    @Inject
    private PhotoDataRepository photoDataRepository;

    @Inject
    protected PhotoDataService(PhotoDataRepository repository) {
        super(repository);
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
            private byte[] data;

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
                processImageData(new PostProcessor() {
                    @Override
                    public void process(ImageFormat format, byte[] output) {
                        data = output;
                    }
                }, photoId, type, format);
                return data;
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

    public void generate(final long photoId, final ContentType type) {
        processImageData(new PostProcessor() {
            @Override
            public void process(ImageFormat format, byte[] output) {
                ScaledPhotoData scaledPhotoData = new ScaledPhotoData();
                scaledPhotoData.setDataAsArray(output);
                scaledPhotoData.setFormat(format.toString());
                scaledPhotoData.setPhotoId(photoId);
                scaledPhotoData.setType(type.toString());
                scaledPhotoDataService.save(scaledPhotoData);
            }
        }, photoId, type, DefaultImageFormat.values());
    }

    private void processImageData(PostProcessor processor,
            final Long photoId,
            final ContentType type,
            final ImageFormat... formats) {
        Photo photo = photoService.find(photoId);
        if (photo == null) {
            return;
        }
        float balance = photo.getCropBalance();
        PhotoData photoData = photoDataRepository.find(photoId);
        byte[] input = photoData.getDataAsArray();
        for (int i = 0; i < formats.length; i++) {
            processor.process(formats[i], imageService.process(input,
                    formats[i],
                    type,
                    balance));
        }
    }
}
