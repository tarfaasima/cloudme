package org.cloudme.loclist.stripes.action;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.location.Location;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.loclist.note.NoteService;

import com.google.inject.Inject;

@UrlBinding( "/action/item/{$event}/{locationId}/{noteItemId}" )
public class ItemActionBean extends AbstractActionBean {
    private static final Log LOG = LogFactory.getLog(ItemActionBean.class);
    private long locationId;
    private long noteItemId;
    @Inject
    private NoteService noteService;
    @Inject
    private LocationService locationService;

    public Resolution tick() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("tick(" + locationId + ", " + noteItemId + ")");
        }
        locationService.tick(new Location(locationId), noteService.getNoteItem(noteItemId), System.currentTimeMillis());
        return null;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long checkinId) {
        this.locationId = checkinId;
    }

    public long getNoteItemId() {
        return noteItemId;
    }

    public void setNoteItemId(long noteItemId) {
        this.noteItemId = noteItemId;
    }
}
