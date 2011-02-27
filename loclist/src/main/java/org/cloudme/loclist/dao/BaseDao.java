package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemIndex;
import org.cloudme.loclist.model.Location;
import org.cloudme.loclist.model.Note;
import org.cloudme.loclist.model.NoteItem;
import org.cloudme.loclist.model.UserProfile;

/**
 * {@link BaseDao} is used to register classes for Objectify-Appengine.
 * 
 * @author Moritz Petersen
 * 
 * @param <E>
 *            The entity type of the DAO.
 */
@SuppressWarnings( "unchecked" )
abstract class BaseDao<E> extends AbstractDao<E> {
    static {
        register(
        // Checkin.class,
        Item.class,
                NoteItem.class,
                Note.class,
                ItemIndex.class,
                Location.class,
                // Tick.class,
                // Update.class,
                UserProfile.class);
    }

    /**
     * Initialize the DAO with the class of the entity type.
     * 
     * @param entityClass
     *            The class of the entity type.
     */
    public BaseDao(Class<E> entityClass) {
        super(entityClass);
    }
}
