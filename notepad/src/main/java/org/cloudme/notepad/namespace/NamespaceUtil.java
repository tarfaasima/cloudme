package org.cloudme.notepad.namespace;

import java.util.ArrayList;
import java.util.Collection;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class NamespaceUtil {
    public static Collection<String> availableNamespaces() {
        Collection<String> namespaces = new ArrayList<String>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        String currentNS = NamespaceManager.get();
        NamespaceManager.set("");
        Query query = new Query("__namespace__");
        PreparedQuery pq = datastore.prepare(query);
        Iterable<Entity> entities = pq.asIterable();
        for (Entity entity : entities) {
            namespaces.add(entity.getKey().getName());
        }
        NamespaceManager.set(currentNS);
        return namespaces;
    }
}
