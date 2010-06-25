package org.cloudme.webgallery.persistence.objectify;

import java.util.Collection;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.model.FlickrMetaData;
import org.cloudme.webgallery.model.IdObject;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.model.ScaledPhotoData;
import org.cloudme.webgallery.persistence.Repository;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public abstract class BaseObjectifyRepository<T extends IdObject<Long>>
        implements Repository<Long, T> {

    static {
        ObjectifyService.register(Album.class);
        ObjectifyService.register(FlickrMetaData.class);
        ObjectifyService.register(Photo.class);
        ObjectifyService.register(PhotoData.class);
        ObjectifyService.register(ScaledPhotoData.class);
    }

    protected abstract class Callback<R> {
        private final boolean needsTransaction;

        public Callback() {
            this(false);
        }

        public Callback(boolean needsTransaction) {
            this.needsTransaction = needsTransaction;
        }

        protected abstract R execute(Objectify ofy);

        boolean isNeedsTransaction() {
            return needsTransaction;
        }
    }

    private final Class<T> baseClass;

    public BaseObjectifyRepository(Class<T> baseClass) {
        this.baseClass = baseClass;
    }

    @Override
    public void delete(final Long id) {
        execute(new Callback<Object>(true) {
            @SuppressWarnings( "unchecked" )
            @Override
            public Object execute(Objectify ofy) {
                ofy.delete(new Key(baseClass, id));
                return null;
            }
        });
    }

    protected <R> R execute(Callback<R> callback) {
        Objectify ofy = callback.needsTransaction ? ObjectifyService
                .beginTransaction() : ObjectifyService.begin();
        Transaction txn = ofy.getTxn();
        try {
            R result = callback.execute(ofy);
            if (callback.needsTransaction) {
                txn.commit();
            }
            return result;
        }
        finally {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @Override
    public T find(final Long id) {
        return execute(new Callback<T>() {
            @Override
            protected T execute(Objectify ofy) {
                return ofy.find(baseClass, id);
            }
        });
    }

    @Override
    public Collection<T> findAll() {
        return execute(new Callback<Collection<T>>() {
            @Override
            protected Collection<T> execute(Objectify ofy) {
                return ofy.query(baseClass).list();
            }
        });
    }

    @Override
    public void save(final T t) {
        execute(new Callback<Object>(true) {
            @Override
            protected Object execute(Objectify ofy) {
                ofy.put(t);
                return null;
            }
        });
    }
}
