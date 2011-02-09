package org.cloudme.webgallery.service;

import java.util.Collection;
import java.util.Iterator;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.flickr.FlickrRequest;
import org.cloudme.webgallery.flickr.FlickrResponse;
import org.cloudme.webgallery.flickr.FlickrRequest.FlickrUrl;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.DefaultImageFormat;
import org.cloudme.webgallery.image.ImageServiceException;
import org.cloudme.webgallery.message.Message;
import org.cloudme.webgallery.model.FlickrMetaData;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.FlickrMetaDataRepository;
import org.cloudme.webgallery.util.UrlUtils;

import com.google.inject.Inject;

public class FlickrService extends
        AbstractService<Long, FlickrMetaData, FlickrMetaDataRepository> {
    @Inject
    CacheService cacheService;
    @Inject
    private PhotoService photoService;
    @Inject
    private PhotoDataService photoDataService;

    public enum Perms {
        READ, WRITE, DELETE;

        @Override
        public String toString() {
            return name().toLowerCase();
        };
    }

    private class FlickrErrorMessage extends Message {
        public FlickrErrorMessage(FlickrResponse res) {
            super("Flickr API error: {0} (code {1})", res.get("/err@msg"), res.get("/err@code"));
            assert !res.isOk();
        }
    }

    @Inject
    protected FlickrService(FlickrMetaDataRepository repository) {
        super(repository);
    }

    /**
     * Returns the {@link FlickrMetaData} instance or null if not yet created.
     * 
     * @return the {@link FlickrMetaData} instance.
     */
    public FlickrMetaData get() {
        return cacheService.cache(new CacheProducer<FlickrMetaData>() {
            public FlickrMetaData produce() {
                Collection<FlickrMetaData> items = findAll();
                if (items == null) {
                    return null;
                }
                switch (items.size()) {
                case 0:
                    return null;

                case 1:
                    return items.iterator().next();

                default:
                    throw new IllegalStateException("Too many FlickrMetaData: " + items.size());
                }
            }
        }, FlickrMetaData.class);
    }

    public void put(FlickrMetaData metaData) {
        cacheService.remove(FlickrMetaData.class);
        Collection<FlickrMetaData> items = findAll();
        if (items != null) {
            for (Iterator<FlickrMetaData> it = items.iterator(); it.hasNext();) {
                delete(it.next().getId());
            }
        }
        super.save(metaData);
    }

    /**
     * Disabled for {@link FlickrService}. Use {@link #put(FlickrMetaData)}
     * instead.
     * 
     * @see #put(FlickrMetaData)
     */
    @Override
    public void save(FlickrMetaData t) {
        throw new UnsupportedOperationException("Use put() instead.");
    }

    public String createLoginLink(Perms perms) {
        FlickrMetaData metaData = get();
        if (metaData == null) {
            return "";
        }
        FlickrRequest request = new FlickrRequest();
        request.setUrl(FlickrUrl.AUTH);
        request.addApiKey(metaData.getKey());
        request.addSecret(metaData.getSecret());
        request.add("perms", perms);
        return request.getHttpRequest().getURI().toString();
    }

    public Message flickrAuthGetToken(String frob) {
        FlickrRequest request = new FlickrRequest();
        request.setUrl(FlickrUrl.REST);
        FlickrMetaData metaData = get();
        request.addApiKey(metaData.getKey());
        request.addSecret(metaData.getSecret());
        request.addMethod("flickr.auth.getToken");
        request.add("frob", frob);
        FlickrResponse response = request.execute();
        if (response.isOk()) {
            metaData.setFullname(response.get("/auth/user@fullname"));
            metaData.setNsid(response.get("/auth/user@nsid"));
            metaData.setPerms(response.get("/auth/perms"));
            metaData.setToken(response.get("/auth/token"));
            metaData.setUsername(response.get("/auth/user@username"));
            put(metaData);
            return new Message("Flickr authentication successful.");
        }
        else {
            return new FlickrErrorMessage(response);
        }
    }

    public Message post(Long photoId, String host, int port) {
        FlickrRequest request = new FlickrRequest();
        request.setUrl(FlickrUrl.UPLOAD);
        FlickrMetaData metaData = get();
        request.addApiKey(metaData.getKey());
        request.addSecret(metaData.getSecret());
        request.addAuthToken(metaData.getToken());
        Photo photo = photoService.find(photoId);
        request.add("title", photo.getName());
        request.add("description",
                "Copyright by Moritz Petersen. View <a href=\""
                        + UrlUtils.createUrl(host, port, photo)
                        + "\">more</a> photos.");
        if (UrlUtils.isLocal(host)) {
            request.add("is_public", 0);
            request.add("is_friend", 0);
            request.add("is_family", 0);
            request.add("hidden", 2);
        }
        request.add("safety_level", 1);
        request.add("content_type", 1);
        request.add("async", 1);
        try {
            byte[] data = photoDataService.getPhotoData(photoId, DefaultImageFormat.LARGE, ContentType.JPEG);
            request.addFile(ContentType.JPEG.toString(),
                    photo.getFileName(),
                    data);
            FlickrResponse response = request.execute();
            if (response.isOk()) {
                return new Message("Upload successful");
            }
            else {
                return new FlickrErrorMessage(response);
            }
        }
        catch (ImageServiceException e) {
            return new Message("No photo data available for {0}. Please try again later.",
                    photoId);
        }
    }
}
