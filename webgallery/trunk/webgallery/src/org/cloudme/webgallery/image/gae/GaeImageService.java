package org.cloudme.webgallery.image.gae;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.ImageService;
import org.springframework.stereotype.Component;

import com.google.appengine.api.images.CompositeTransform;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ImagesService.OutputEncoding;
import com.google.apphosting.api.ApiProxy.OverQuotaException;

@Component
public class GaeImageService implements ImageService {
    private static final Logger LOG = Logger.getLogger(GaeImageService.class);
    
    public byte[] process(byte[] data, ImageFormat format, ContentType type, float balance) {
        Image image = ImagesServiceFactory.makeImage(data);
        CompositeTransform tx = ImagesServiceFactory.makeCompositeTransform();
        if (format.isCrop()) {
            float w1 = image.getWidth();
            float h1 = image.getHeight();
            float w2 = format.getWidth();
            float h2 = format.getHeight();
            Crop c = new Crop(w1, h1, w2, h2, balance);
            tx.concatenate(ImagesServiceFactory.makeCrop(c.x, c.y, c.x + c.width, c.y + c.height));
        }
        tx.concatenate(ImagesServiceFactory.makeResize(format.getWidth(), format.getHeight()));
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        OutputEncoding outputEncoding = OutputEncoding.valueOf(type.name());
        try {
            return imagesService.applyTransform(tx, image, outputEncoding).getImageData();
        }
        catch (OverQuotaException e) {
            LOG.warn("Over quota.", e);
            return getOverQuotaImage(format);
        }
    }

    private byte[] getOverQuotaImage(ImageFormat format) {
        int max = Math.max(format.getWidth(), format.getHeight());
        String overQuotaFileName = "over_quota_" + max + ".jpg";
        try {
            InputStream in = getClass().getResourceAsStream(overQuotaFileName);
            if (in == null) {
                in = getClass().getResourceAsStream("over_quota_198.jpg");
            }
            return IOUtils.toByteArray(in);
        }
        catch (IOException e) {
            LOG.debug(e);
            return null;
        }
    }
}
