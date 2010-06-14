package org.cloudme.webgallery.image;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * Loads "over qouta" images from static resources. The resource loader is
 * pluggable and can be implemented in the overriding class.
 * 
 * @author Moritz Petersen
 */
public abstract class OverQuotaImageLoader {
    /**
     * Loads the "over qouta" image for the given format.
     * 
     * @param format
     *            The image format.
     * @return The "over quota" image or <code>null</code> if an error occurred.
     */
    public byte[] load(ImageFormat format) {
        int max = Math.max(format.getWidth(), format.getHeight());
        String overQuotaFileName = "over_quota_" + max + ".jpg";
        InputStream in = null;
        try {
            in = loadResource(overQuotaFileName);
            if (in == null) {
                in = loadResource("over_quota_198.jpg");
            }
            if (in == null) {
                return null;
            }
            return IOUtils.toByteArray(in);
        }
        catch (IOException e) {
            return null;
        }
        finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * Implement a custom resource loader here.
     * 
     * @param fileName
     *            The name of the file that should be loaded.
     * @return The input stream pointing to the file or <code>null</code> if an
     *         error occurred.
     */
    protected abstract InputStream loadResource(String fileName);
}
