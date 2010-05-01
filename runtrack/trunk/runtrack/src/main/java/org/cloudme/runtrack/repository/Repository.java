package org.cloudme.runtrack.repository;

import java.lang.reflect.Field;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.vercer.engine.persist.ObjectDatastore;
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore;

public class Repository<T> {
    private final Class<T> baseClass;

    public Repository(Class<T> baseClass) {
        this.baseClass = baseClass;
    }

    public void save(T t) {
        Key key = getObjectDatastore().store(t);
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field
                    .getAnnotation(com.vercer.engine.persist.annotation.Key.class) != null) {
                try {
                    field.setAccessible(true);
                    field.set(t, key.getId());
                    break;
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public T load(long id) {
        return (T) getObjectDatastore().load(baseClass, id);
    }

    public void delete(T t) {
        System.out.println(t);
        ObjectDatastore datastore = getObjectDatastore();
        Transaction tx = datastore.beginTransaction();
        datastore.delete(t);
        tx.commit();
    }

    private ObjectDatastore getObjectDatastore() {
        return new AnnotationObjectDatastore();
    }
}
