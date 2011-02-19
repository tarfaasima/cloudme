package org.cloudme.loclist.stripes.action;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.model.Note;

import com.google.inject.Inject;

@UrlBinding( "/action/index/{$event}" )
public class IndexActionBean extends AbstractActionBean {
    @Inject
    private ItemService itemService;
    private List<Note> notes;
    @ValidateNestedProperties( { @Validate( field = "name", required = true ) } )
    private Note note;

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        notes = itemService.getNotes();
        return resolve("index.jsp");
    }

    public Resolution create() {
        itemService.put(note);
        return new RedirectResolution("/action/index");
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }
}
