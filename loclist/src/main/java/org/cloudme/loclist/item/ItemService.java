package org.cloudme.loclist.item;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.ItemIndexDao;
import org.cloudme.loclist.dao.NoteDao;
import org.cloudme.loclist.dao.NoteItemDao;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemIndex;
import org.cloudme.loclist.model.Location;
import org.cloudme.loclist.model.Note;
import org.cloudme.loclist.model.NoteItem;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.google.inject.Inject;

public class ItemService {
    @Inject
    private ItemDao itemDao;
    @Inject
    private NoteDao noteDao;
    @Inject
    private NoteItemDao noteItemDao;
    @Inject
    private ItemIndexDao itemIndexDao;
    private final ItemIndexEngine engine = new ItemIndexEngine();

    /**
     * Creates a new {@link Item} and adds it to the {@link Note} with the given
     * id. If another {@link Item} with the same text already exists, the
     * {@link Item} will not be created, but added to the {@link Note}. If the
     * {@link Item} is already added to the {@link Note} it will not be added
     * again.
     * 
     * @param note
     *            The {@link Note}
     * @param item
     *            The {@link Item}
     * @param attribute
     *            The attribute of the {@link NoteItem}
     */
    public void createItem(Note note, Item item, String attribute) {
        Item existingItem = itemDao.findSingleByText(item.getText());
        if (existingItem != null) {
            item.setId(existingItem.getId());
            item.setText(existingItem.getText());
        }
        else {
            itemDao.save(item);
        }
        addOrRemove(note, item, attribute);
    }

    /**
     * Persists a {@link Note}.
     * 
     * @param note
     *            The {@link Note}
     */
    public void put(Note note) {
        noteDao.save(note);
    }

    /**
     * Registers a tick for the given {@link Location} and {@link NoteItem} and
     * triggers index update.
     * 
     * @param location
     *            The {@link Location}
     * @param noteItem
     *            The {@link NoteItem}
     */
    public void tick(Location location, NoteItem noteItem) {
        if (noteItem.isTicked()) {
            return;
        }
        noteItem.setTicked(true);
        noteItemDao.save(noteItem);

        Item item = new Item(noteItem.getItemId());
        ItemIndex itemIndex = itemIndexDao.findBy(location, item);
        ItemIndex lastItemIndex = itemIndexDao.findLastBy(location);
        long timestamp = System.currentTimeMillis();
        itemIndex = engine.update(location, item, timestamp, itemIndex, lastItemIndex);
        itemIndexDao.save(itemIndex);
    }

    /**
     * Returns all {@link Note}s ordered by name.
     * 
     * @return A list of {@link Note}s ordered by name.
     */
    public List<Note> getNotes() {
        return noteDao.listAll();
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

    public void orderByLocation(Location location, List<NoteItem> noteItems) {
        Iterable<ItemIndex> itemIndexs = itemIndexDao.findBy(location);
        final Map<Long, ItemIndex> itemIndexMap = new HashMap<Long, ItemIndex>();
        for (ItemIndex itemIndex : itemIndexs) {
            itemIndexMap.put(itemIndex.getItemId(), itemIndex);
        }
        Collections.sort(noteItems, new NoteItemComparator(itemIndexMap));
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
        for (Item item : itemDao.findAll()) {
            if (!itemIds.contains(item.getId())) {
                NoteItem noteItem = new NoteItem();
                noteItem.setItemId(item.getId());
                noteItem.setText(item.getText());
                noteItems.add(noteItem);
            }
        }
        return noteItems;
    }

    public Note getNote(Long noteId) {
        return noteDao.find(noteId);
    }

    /**
     * Deletes the {@link Note} and all {@link NoteItem}s.
     * 
     * @param noteId
     *            The id of the {@link Note}
     */
    public void deleteNote(Long noteId) {
        noteDao.delete(noteId);
        noteItemDao.deleteByNoteId(noteId);
    }

    /**
     * Deletes the {@link Item}, the {@link NoteItem} and the {@link ItemIndex}.
     * 
     * @param itemId
     *            The id of the {@link Item}.
     */
    public void deleteItem(Long itemId) {
        itemDao.delete(itemId);
        noteItemDao.deleteByItemId(itemId);
        itemIndexDao.deleteByItemId(itemId);
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
        noteItemDao.save(noteItem);
    }

    public Item getItem(Long itemId) {
        return itemDao.find(itemId);
    }
}
