package org.cloudme.loclist.stripes.action;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.Note;
import org.cloudme.loclist.model.NoteItem;

import com.google.inject.Inject;

@UrlBinding( "/action/edit/{noteId}/{$event}/{itemId}/{attribute}" )
public class EditActionBean extends AbstractActionBean {
    private static final Log LOG = LogFactory.getLog(EditActionBean.class);
    @Inject
    private ItemService itemService;
    private Long itemId;
    private Long noteId;
    @ValidateNestedProperties( { @Validate( field = "text", required = true ) } )
    private Item item;
    private String attribute;
    private Collection<NoteItem> noteItems;
    private Note note;

    @DontValidate
    @DefaultHandler
    public Resolution index() {
        note = itemService.getNote(noteId);
        noteItems = itemService.getAllNoteItems(note);
        return resolve("edit.jsp");
    }

    public Resolution create() {
        itemService.createItem(new Note(noteId), item, attribute);
        return new RedirectResolution("/action/edit/" + noteId);
    }

    @DontValidate
    public Resolution delete() {
        itemService.deleteItem(itemId);
        return new RedirectResolution("/action/edit/" + noteId);
    }

    @DontValidate
    public Resolution addOrRemove() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("attribute = " + attribute);
        }
        Item item = itemService.getItem(itemId);
        itemService.addOrRemove(new Note(noteId), item, attribute);
        return null;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long id) {
        this.itemId = id;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public void setNoteItems(Collection<NoteItem> noteItems) {
        this.noteItems = noteItems;
    }

    public Collection<NoteItem> getNoteItems() {
        return noteItems;
    }
}
