package org.cloudme.webgallery.image.gae;

import java.util.HashMap;
import java.util.Map;

import org.cloudme.webgallery.image.ImageParameter;
import org.cloudme.webgallery.image.ImageService;
import org.springframework.stereotype.Component;

import com.google.appengine.api.images.CompositeTransform;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ImagesService.OutputEncoding;

@Component
public class GaeImageService implements ImageService {
    private final Map<String, OutputEncoding> outputEncodingMap = new HashMap<String, OutputEncoding>() {
        {
            put("JPG", OutputEncoding.JPEG);
        }
    };

    public byte[] process(byte[] data, ImageParameter parameter, String format) {
        Image image = ImagesServiceFactory.makeImage(data);
        CompositeTransform tx = ImagesServiceFactory.makeCompositeTransform();
        if (parameter.isSquare()) {
            float width = image.getWidth();
            float height = image.getHeight();
            float min = Math.min(width, height);
            float x = (width - min) / 2;
            float y = (height - min) / 2;
            tx.concatenate(ImagesServiceFactory.makeCrop(x / width, y / height, (x + min) / width, (y + min) / height));
        }
        tx.concatenate(ImagesServiceFactory.makeResize(parameter.getSize(), parameter.getSize()));
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        OutputEncoding outputEncoding = getOutputEncoding(format);
        return imagesService.applyTransform(tx, image, outputEncoding).getImageData();
    }

    private OutputEncoding getOutputEncoding(String format) {
        format = format.trim().toUpperCase();
        try {
            return OutputEncoding.valueOf(format);
        }
        catch (IllegalArgumentException e) {
            return outputEncodingMap.get(format);
        }
    }
}
