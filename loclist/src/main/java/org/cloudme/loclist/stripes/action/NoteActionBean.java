package org.cloudme.loclist.stripes.action;

import java.util.List;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.cloudme.loclist.location.Location;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.loclist.note.Note;
import org.cloudme.loclist.note.NoteItem;
import org.cloudme.loclist.note.NoteService;
import org.cloudme.loclist.stripes.validation.GeoCoordinateConverter;
import org.cloudme.sugar.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/action/note/{$event}/{id}/{latitude}/{longitude}" )
public class NoteActionBean extends AbstractActionBean {
    @Inject
    private NoteService noteService;
    @Inject
    private LocationService locationService;
    private Long id;
    @Validate( converter = GeoCoordinateConverter.class )
    private float latitude;
    @Validate( converter = GeoCoordinateConverter.class )
    private float longitude;
    private List<NoteItem> noteItems;
    private Note note;
    private Location location;

    public Resolution checkin() {
        location = locationService.checkin(latitude, longitude);
        note = noteService.find(id);
        noteItems = noteService.getNoteItems(note);
        locationService.sortNoteItems(location, noteItems);
        return resolve("note.jsp");
    }

    public Resolution delete() {
        noteService.delete(id);
        return new RedirectResolution("/action/index");
    }

    public Resolution reset() {
        noteService.resetTicks(new Note(id));
        return checkin();
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

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
