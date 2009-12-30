// created 30.12.2009 10:28:46 by Moritz Petersen
package org.cloudme.webgallery.cache;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.cloudme.webgallery.image.ImageParameter;

public class CacheKey implements Serializable {
    private final String photoId;
    private final ImageParameter parameter;
    private final String format;

    public CacheKey(String photoId, ImageParameter parameter, String format) {
        assert photoId != null;
        assert parameter != null;
        assert format != null;
        this.photoId = photoId;
        this.parameter = parameter;
        this.format = format;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        CacheKey key = (CacheKey) obj;
        return new EqualsBuilder().append(photoId, key.photoId).append(parameter, key.parameter).append(format, key.format).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(photoId).append(parameter).append(format).toHashCode();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append(photoId).append(parameter).append(format).toString();
    }
}