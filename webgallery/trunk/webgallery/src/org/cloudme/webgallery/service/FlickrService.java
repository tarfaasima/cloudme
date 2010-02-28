package org.cloudme.webgallery.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import javax.mail.internet.MimeMultipart;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.flickr.FlickrRequest;
import org.cloudme.webgallery.flickr.FlickrResponse;
import org.cloudme.webgallery.flickr.FlickrRequest.FlickrUrl;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;
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

    private class FlickrImageFormat implements ImageFormat {
        public int getHeight() {
            return 500;
        }

        public int getWidth() {
            return 500;
        }

        public boolean isCrop() {
            return false;
        }
    }

    @Autowired
    protected FlickrService(FlickrMetaDataRepository repository) {
        super(repository);
    }

    public FlickrMetaData get() {
        return cacheService.cache(new CacheProducer<FlickrMetaData>() {
            public FlickrMetaData produce() {
                Collection<FlickrMetaData> items = findAll();
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
        cacheService.update(metaData, FlickrMetaData.class);
        save(metaData);
    }

    public String createLoginLink(Perms perms) {
        FlickrMetaData metaData = get();
        if (metaData == null) {
            return "";
        }
        FlickrRequest request = new FlickrRequest();
        request.url(FlickrUrl.AUTH);
        request.apiKey(metaData.getKey());
        request.secret(metaData.getSecret());
        request.append("perms", perms);
        return request.toUrl();
    }

    public void flickrAuthGetToken(String frob) {
        FlickrRequest request = new FlickrRequest();
        request.url(FlickrUrl.REST);
        FlickrMetaData metaData = get();
        request.apiKey(metaData.getKey());
        request.secret(metaData.getSecret());
        request.method("flickr.auth.getToken");
        request.append("frob", frob);
        FlickrResponse response = new FlickrResponse(openStream(request));
        metaData.setFullname(response.get("/auth/user@fullname"));
        metaData.setNsid(response.get("/auth/user@nsid"));
        metaData.setPerms(response.get("/auth/perms"));
        metaData.setToken(response.get("/auth/token"));
        metaData.setUsername(response.get("/auth/user@username"));
        put(metaData);
    }

    private InputStream openStream(FlickrRequest request) {
        try {
            URL url = new URL(request.toUrl());
            URLConnection con = url.openConnection();
            MimeMultipart multipart = request.getMultipart();
            if (multipart != null) {
                con.setDoInput(false);
                OutputStream out = con.getOutputStream();
                multipart.writeTo(out);
                out.close();
            }
            return con.getInputStream();
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void post(Long photoId) {
        FlickrRequest request = new FlickrRequest();
        request.url(FlickrUrl.UPLOAD);
        FlickrMetaData metaData = get();
        request.apiKey(metaData.getKey());
        request.secret(metaData.getSecret());
        Photo photo = photoService.find(photoId);
        request.append("title", photo.getName());
        request.append("description", "Taken by Moritz Petersen. View <a href=\"http://photos.moritzpetersen.de/gallery/album/" + photo.getAlbumId() + "/photo/" + photoId + "\">large</a>");
        request.append("is_public", 0);
        request.append("is_friend", 0);
        request.append("is_family", 0);
        request.append("safety_level", 1);
        request.append("content_type", 1);
        request.append("hidden", 2);
        byte[] data = photoDataService.getPhotoData(photoId, new FlickrImageFormat(), ContentType.JPEG);
        request.appendPost("photo", ContentType.JPEG.toString(), photo.getFileName(), data);

    }
}
