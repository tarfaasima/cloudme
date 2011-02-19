package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.NoteItem;
import org.cloudme.loclist.model.Note;
import org.cloudme.loclist.model.ItemIndex;
import org.cloudme.loclist.model.Location;
import org.cloudme.loclist.model.Update;
import org.cloudme.loclist.model.UserProfile;

@SuppressWarnings( "unchecked" )
abstract class BaseDao<T> extends AbstractDao<T> {
    static {
        register(Checkin.class, Item.class, NoteItem.class, Note.class, ItemIndex.class, Location.class,
        // Tick.class,
                Update.class,
                UserProfile.class);
    }

    public BaseDao(Class<T> baseClass) {
        super(baseClass);
    }
}
