package org.cloudme.webgallery.persistence.jdo;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;

import org.cloudme.webgallery.model.ScaledPhotoData;
import org.cloudme.webgallery.persistence.ScaledPhotoDataRepository;
import org.junit.Test;


public class JdoScaledPhotoDataRepositoryTest {
    @Test
    public void testQuery() {
        ScaledPhotoDataRepository repo = new JdoScaledPhotoDataRepository() {
            @SuppressWarnings( "unchecked" )
            @Override
            protected Collection<ScaledPhotoData> queryCollection(String filter) {
                assertEquals("photoId == 132443 && format == 't' && type == 'image/jpeg'",
                        filter);
                return Collections.EMPTY_LIST;
            }
        };
        repo.find(132443L, "t", "image/jpeg");
    }
}
