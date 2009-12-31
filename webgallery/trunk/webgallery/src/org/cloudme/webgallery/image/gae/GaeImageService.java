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
    public byte[] process(byte[] data, ImageFormat format, ContentType type) {
        Image image = ImagesServiceFactory.makeImage(data);
        CompositeTransform tx = ImagesServiceFactory.makeCompositeTransform();
        if (format.isSquare()) {
            float width = image.getWidth();
            float height = image.getHeight();
            float min = Math.min(width, height);
            float x = (width - min) / 2;
            float y = (height - min) / 2;
            tx.concatenate(ImagesServiceFactory.makeCrop(x / width, y / height, (x + min) / width, (y + min) / height));
        }
        tx.concatenate(ImagesServiceFactory.makeResize(format.getSize(), format.getSize()));
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        OutputEncoding outputEncoding = OutputEncoding.valueOf(type.name());
        return imagesService.applyTransform(tx, image, outputEncoding).getImageData();
    }
}
