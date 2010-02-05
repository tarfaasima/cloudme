package org.cloudme.webgallery.image.gae;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.ImageService;
import org.springframework.stereotype.Component;

import com.google.appengine.api.images.CompositeTransform;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ImagesService.OutputEncoding;

@Component
public class GaeImageService implements ImageService {
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
        return imagesService.applyTransform(tx, image, outputEncoding).getImageData();
    }
}
