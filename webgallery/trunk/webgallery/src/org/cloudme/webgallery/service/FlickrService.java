package org.cloudme.webgallery.service;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.flickr.FlickrRequest;
import org.cloudme.webgallery.flickr.FlickrResponse;
import org.cloudme.webgallery.flickr.FlickrRequest.FlickrUrl;
import org.cloudme.webgallery.model.FlickrMetaData;
import org.cloudme.webgallery.persistence.FlickrMetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlickrService extends AbstractService<Long, FlickrMetaData> {
    @Autowired
    private CacheService cacheService;

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

    public FlickrMetaData get() {
        return cacheService.cache(new CacheProducer<FlickrMetaData>() {
            public FlickrMetaData produce() {
                Collection<FlickrMetaData> items = findAll();
                switch (items.size()) {
                case 0:
                    return new FlickrMetaData();

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
        FlickrRequest request = new FlickrRequest();
        request.url(FlickrUrl.AUTH);
        FlickrMetaData metaData = get();
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
            return url.openStream();
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
