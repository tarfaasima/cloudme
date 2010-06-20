package org.cloudme.webgallery.persistence.jdo;

import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.cloudme.webgallery.model.ScaledPhotoData;
import org.cloudme.webgallery.persistence.ScaledPhotoDataRepository;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.stereotype.Repository;

@Repository
public class JdoScaledPhotoDataRepository extends
        AbstractJdoRepository<Long, ScaledPhotoData> implements
        ScaledPhotoDataRepository {

    public JdoScaledPhotoDataRepository() {
        super(ScaledPhotoData.class);
    }

    public Collection<ScaledPhotoData> find(Long photoId,
            String format,
            String type) {
        return queryCollection(String
                .format("photoId == %s && format == '%s' && type == '%s'",
                photoId,
                format,
                type));
    }

    public void deleteByPhotoId(final long photoId) {
        getJdoTemplate().execute(new JdoCallback<ScaledPhotoData>() {
            public ScaledPhotoData doInJdo(PersistenceManager pm)
                    throws JDOException {
                Query query = pm.newQuery(ScaledPhotoData.class);
                query.setFilter("photoId == " + photoId);
                query.execute();
                return null;
            }
        });
    }
}
