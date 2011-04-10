package org.cloudme.loclist.note;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.cloudme.loclist.item.Item;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.sugar.AbstractService;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.google.inject.Inject;

public class NoteService extends AbstractService<Note> {
    @Inject
    private ItemService itemService;
    @Inject
    private NoteItemDao noteItemDao;

    @Inject
    public NoteService(NoteDao noteDao) {
        super(noteDao);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
        noteItemDao.deleteByNoteId(id);
    }

    /**
     * Adds or removes an {@link Item} to a {@link Note} by creating or deleting
     * a {@link NoteItem}. If the {@link NoteItem} already exists it will be
     * deleted only if the <tt>attribute</tt> is not empty, otherwise it will be
     * created.
     * 
     * @param note
     *            The {@link Note}
     * @param item
     *            The {@link Item}
     * @param attribute
     *            The attribute of the {@link NoteItem}. If the {@link NoteItem}
     *            already exists and the attribute is not empty, it will be
     *            updated with the given attribute. Otherwise it will be
     *            deleted.
     */
    public void addOrRemove(Note note, Item item, String attribute) {
        item = itemService.put(item);
        NoteItem noteItem = noteItemDao.findSingleBy(note, item);
        if (noteItem != null) {
            if (StringUtil.isEmptyOrWhitespace(attribute)) {
                noteItemDao.delete(noteItem.getId());
                return;
            }
        }
        else {
            noteItem = new NoteItem();
            noteItem.setItemId(item.getId());
            noteItem.setNoteId(note.getId());
            noteItem.setText(item.getText());
        }
        noteItem.setAttribute(attribute);
        noteItemDao.put(noteItem);
    }

    /**
     * Returns all {@link NoteItem}s. The {@link Note} is required to indicate
     * if the {@link NoteItem} is contained in the {@link Note}. In that case,
     * the {@link NoteItem#isInNote()} will return <tt>true</tt>.
     * 
     * @param note
     *            The {@link Note}.
     * @return all {@link NoteItem}s, indicating if contained in the
     *         {@link Note}.
     */
    public Collection<NoteItem> getAllNoteItems(Note note) {
        SortedSet<NoteItem> noteItems = new TreeSet<NoteItem>(getNoteItems(note));
        Set<Long> itemIds = new HashSet<Long>(noteItems.size());
        for (NoteItem noteItem : noteItems) {
            itemIds.add(noteItem.getItemId());
            noteItem.setInNote(true);
        }
        for (Item item : itemService.findAll()) {
            if (!itemIds.contains(item.getId())) {
                NoteItem noteItem = new NoteItem();
                noteItem.setItemId(item.getId());
                noteItem.setText(item.getText());
                noteItems.add(noteItem);
            }
        }
        return noteItems;
    }

    /**
     * Returns the {@link NoteItem} of the given id.
     * 
     * @param noteItemId
     *            The id of the {@link NoteItem}
     * @return The {@link NoteItem}
     */
    public NoteItem getNoteItem(long noteItemId) {
        return noteItemDao.find(noteItemId);
    }

    /**
     * Returns all {@link NoteItem}s of the given {@link Note}.
     * 
     * @param note
     *            The {@link Note}.
     * @return All {@link NoteItem}s of the given {@link Note}.
     */
    public List<NoteItem> getNoteItems(Note note) {
        return noteItemDao.listBy(note);
    }

    public void put(NoteItem noteItem) {
        noteItemDao.put(noteItem);
    }

    public void deleteNoteItemByItemId(Long itemId) {
        noteItemDao.deleteByItemId(itemId);
    }

    /**
     * Resets the ticks of the {@link NoteItem}s of the given {@link Note}.
     * 
     * @param note
     *            The {@link Note}.
     */
    public void resetTicks(Note note) {
        List<NoteItem> noteItems = noteItemDao.listTickedBy(note);
        for (NoteItem noteItem : noteItems) {
            if (noteItem.isTicked()) {
                noteItem.setTicked(false);
            }
        }
        noteItemDao.put(noteItems);
    }

}
