package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.loclist.model.Tick;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

public class TickDao extends AbstractDao<Tick> {
    public TickDao() {
        super(Tick.class);
    }

    public void deleteByCheckinAndItem(final Long checkinId, final Long itemId) {
        Query<Tick> query = (Query<Tick>) findAll(filter("checkinId", checkinId), filter("itemId", itemId));
        final QueryResultIterable<Key<Tick>> keys = query.fetchKeys();
        execute(new Callback<Void>(true) {
            @Override
            protected Void execute(Objectify ofy) {
                ofy.delete(keys);
                return null;
            }
        });
    }

    public Iterable<Tick> findByCheckin(Long checkinId) {
        return findAll(filter("checkinId", checkinId), orderBy("timestamp"));
    }
}
