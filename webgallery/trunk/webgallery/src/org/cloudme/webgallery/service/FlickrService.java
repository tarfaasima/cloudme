package org.cloudme.webgallery.service;

import java.util.Collection;
import java.util.Iterator;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.flickr.FlickrRequest;
import org.cloudme.webgallery.flickr.FlickrResponse;
import org.cloudme.webgallery.flickr.FlickrRequest.FlickrUrl;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.model.FlickrMetaData;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.FlickrMetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlickrService extends AbstractService<Long, FlickrMetaData> {
    @Autowired
    protected CacheService cacheService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private PhotoDataService photoDataService;

    public enum Perms {
        READ, WRITE, DELETE;

        @Override
        public String toString() {
            return name().toLowerCase();
        };
    }

    @Autowired
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
     * Disabled for {@link FlickrService}. Use {@link #put(FlickrMetaData)} instead.
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

    public void flickrAuthGetToken(String frob) {
        FlickrRequest request = new FlickrRequest();
        request.setUrl(FlickrUrl.REST);
        FlickrMetaData metaData = get();
        request.addApiKey(metaData.getKey());
        request.addSecret(metaData.getSecret());
        request.addMethod("flickr.auth.getToken");
        request.add("frob", frob);
        FlickrResponse response = request.execute();
        metaData.setFullname(response.get("/auth/user@fullname"));
        metaData.setNsid(response.get("/auth/user@nsid"));
        metaData.setPerms(response.get("/auth/perms"));
        metaData.setToken(response.get("/auth/token"));
        metaData.setUsername(response.get("/auth/user@username"));
        put(metaData);
    }

    public void post(Long photoId) {
        FlickrRequest request = new FlickrRequest();
        request.setUrl(FlickrUrl.UPLOAD);
        FlickrMetaData metaData = get();
        request.addApiKey(metaData.getKey());
        request.addSecret(metaData.getSecret());
        request.addAuthToken(metaData.getToken());
        Photo photo = photoService.find(photoId);
        request.add("title", photo.getName());
        request.add("description", "Coypright by Moritz Petersen. View <a href=\"http://photos.moritzpetersen.de/gallery/album/" + photo.getAlbumId() + "/photo/" + photoId + "\">large</a>");
        request.add("is_public", 0);
        request.add("is_friend", 0);
        request.add("is_family", 0);
        request.add("safety_level", 1);
        request.add("content_type", 1);
        request.add("hidden", 2);
        request.add("async", 1);
        byte[] data = photoDataService.getPhotoData(photoId, new FlickrImageFormat(), ContentType.JPEG);
        request.addFile(ContentType.JPEG.toString(), photo.getFileName(), data);
        FlickrResponse response = request.execute();
        if (response.isOk()) {
        }
        else {
            System.out.println("fail");
            System.out.println("code: " + response.get("/err@code"));
            System.out.println("msg: " + response.get("/err@msg"));
        }
    }
}
