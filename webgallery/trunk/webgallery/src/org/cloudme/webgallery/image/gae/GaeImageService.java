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
        CompositeTransform tx = ImagesServiceFactory.makeCompositeTransform();
        tx.concatenate(ImagesServiceFactory.makeResize(parameter.getSize(), parameter.getSize()));
        Image image = ImagesServiceFactory.makeImage(data);
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
