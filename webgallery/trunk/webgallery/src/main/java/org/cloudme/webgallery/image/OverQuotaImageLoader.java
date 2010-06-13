package org.cloudme.webgallery.image;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public abstract class OverQuotaImageLoader {
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

    protected abstract InputStream loadResource(String overQuotaFileName);
}
