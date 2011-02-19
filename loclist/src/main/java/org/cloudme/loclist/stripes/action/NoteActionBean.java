package org.cloudme.loclist.stripes.action;

import java.util.List;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.loclist.model.Note;
import org.cloudme.loclist.model.NoteItem;
import org.cloudme.loclist.stripes.validation.GeoCoordinateConverter;

import com.google.inject.Inject;

@UrlBinding( "/action/note/{$event}/{id}/{latitude}/{longitude}" )
public class NoteActionBean extends AbstractActionBean {
    @Inject
    private LocationService locationService;
    @Inject
    private ItemService itemService;
    private Long id;
    @Validate( converter = GeoCoordinateConverter.class )
    private float latitude;
    @Validate( converter = GeoCoordinateConverter.class )
    private float longitude;
    private List<NoteItem> noteItems;
    private Note note;
    private Long checkinId;

    public Resolution checkin() {
        checkinId = locationService.checkin(latitude, longitude).getId();
        noteItems = itemService.getNoteItems(id);
        itemService.orderByCheckin(checkinId, noteItems);
        note = itemService.getNote(id);
        return resolve("note.jsp");
    }

    public Resolution delete() {
        itemService.deleteNote(id);
        return new RedirectResolution("/action/index");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setNoteItems(List<NoteItem> noteItems) {
        this.noteItems = noteItems;
    }

    public List<NoteItem> getNoteItems() {
        return noteItems;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public void setCheckinId(Long checkinId) {
        this.checkinId = checkinId;
    }

    public Long getCheckinId() {
        return checkinId;
    }
}
