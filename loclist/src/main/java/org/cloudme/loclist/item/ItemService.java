package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.AbstractDao.filter;
import static org.cloudme.gaestripes.AbstractDao.orderBy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.gaestripes.QueryOperator;
import org.cloudme.loclist.dao.CheckinDao;
import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.ItemIndexDao;
import org.cloudme.loclist.dao.NoteDao;
import org.cloudme.loclist.dao.NoteItemDao;
import org.cloudme.loclist.dao.UpdateDao;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemIndex;
import org.cloudme.loclist.model.Note;
import org.cloudme.loclist.model.NoteItem;
import org.cloudme.loclist.model.Tick;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.google.inject.Inject;

public class ItemService {
    private static final Log LOG = LogFactory.getLog(ItemService.class);
    /**
     * Timeout is one day.
     */
    private static final long TIMEOUT = 24 * 60 * 60 * 1000;
    @Inject
    private ItemDao itemDao;
    @Inject
    private NoteDao noteDao;
    @Inject
    private NoteItemDao noteItemDao;
    // @Inject
    // private TickDao tickDao;
    @Inject
    private CheckinDao checkinDao;
    @Inject
    private ItemIndexDao itemIndexDao;
    @Inject
    private UpdateDao updateDao;

    /**
     * Creates a new {@link Item} and adds it to the {@link Note} with the given
     * id. If another {@link Item} with the same text already exists, the
     * {@link Item} will not be created, but added to the {@link Note}. If the
     * {@link Item} is already added to the {@link Note} it will not be added
     * again.
     * 
     * @param noteId
     *            The id of the {@link Note}.
     * @param item
     *            The {@link Item}.
     * @param attribute
     *            TODO
     */
    public void createItem(Long noteId, Item item, String attribute) {
        Item existingItem = itemDao.findSingle("text", item.getText());
        if (existingItem != null) {
            item.setId(existingItem.getId());
            item.setText(existingItem.getText());
        }
        else {
            itemDao.save(item);
        }
        addOrRemove(noteId, item.getId(), attribute);
    }

    public void put(Note note) {
        noteDao.save(note);
    }

    public void tick(Long checkinId, Long noteItemId) {
        NoteItem noteItem = noteItemDao.find(noteItemId);
        if (noteItem.isTicked()) {
            return;
        }
        noteItem.setTicked(true);
        noteItemDao.save(noteItem);

        updateItemIndex(checkinId, noteItem);
    }

    private void updateItemIndex(Long checkinId, NoteItem noteItem) {
        Long itemId = noteItem.getItemId();
        long locationId = checkinDao.find(checkinId).getLocationId();
        ItemIndex itemIndex = itemIndexDao.findByLocationAndItem(locationId, itemId);
        if (itemIndex == null) {
            itemIndex = new ItemIndex();
            itemIndex.setIndex(-1);
            itemIndex.setItemId(itemId);
            itemIndex.setLocationId(locationId);
        }

        ItemIndex lastItemIndex = itemIndexDao.findLastByLocation(locationId);

        if (lastItemIndex != null) {
            int lastIndex = lastItemIndex.getIndex();
            if (isAlreadyLower(itemIndex, lastIndex)) {
                return;
            }
            itemIndex.setIndex(lastIndex + 1);
        }
        else {
            itemIndex.setIndex(0);
        }

        itemIndexDao.save(itemIndex);
    }

    private boolean isOutdated(ItemIndex lastItemIndex) {
        return System.currentTimeMillis() - lastItemIndex.getLastUpdate() > TIMEOUT;
    }

    private boolean isAlreadyLower(ItemIndex itemIndex, int lastIndex) {
        return itemIndex.getIndex() > lastIndex;
    }

    public List<Note> getNotes() {
        return noteDao.listAll(orderBy("name"));
    }

    public List<NoteItem> getNoteItems(Long noteId) {
        return noteItemDao.listByNote(noteId);
    }

    public void orderByCheckin(Long checkinId, List<NoteItem> noteItems) {
        Checkin checkin = checkinDao.find(checkinId);
        if (checkin != null) {
            Iterable<ItemIndex> itemIndexs = itemIndexDao.findByLocation(checkin.getLocationId());
            final Map<Long, ItemIndex> itemIndexMap = new HashMap<Long, ItemIndex>();
            for (ItemIndex itemIndex : itemIndexs) {
                itemIndexMap.put(itemIndex.getItemId(), itemIndex);
            }
            Collections.sort(noteItems, new NoteItemComparator(itemIndexMap));
        }
    }

    public Collection<NoteItem> getAllNoteItems(Long noteId) {
        SortedSet<NoteItem> noteItems = new TreeSet<NoteItem>(getNoteItems(noteId));
        Set<Long> itemIds = new HashSet<Long>(noteItems.size());
        for (NoteItem noteItem : noteItems) {
            itemIds.add(noteItem.getItemId());
            noteItem.setInList(true);
        }
        for (Item item : itemDao.findAll(orderBy("text"))) {
            if (!itemIds.contains(item.getId())) {
                NoteItem noteItem = new NoteItem();
                noteItem.setItemId(item.getId());
                noteItem.setText(item.getText());
                noteItems.add(noteItem);
            }
        }
        return noteItems;
    }

    public Note getNote(Long id) {
        return noteDao.find(id);
    }

    /**
     * Deletes the {@link Note} and all {@link NoteItem}s.
     * 
     * @param id
     *            The id of the {@link Note}
     */
    public void deleteNote(Long id) {
        noteDao.delete(id);
        noteItemDao.deleteAll(filter("noteId", id));
    }

    /**
     * Deletes the {@link Item}, the {@link NoteItem}, the {@link ItemIndex} and
     * the {@link Tick}.
     * 
     * @param id
     *            The id of the {@link Item}.
     */
    public void deleteItem(Long id) {
        itemDao.delete(id);
        QueryOperator filter = filter("itemId", id);
        noteItemDao.deleteAll(filter);
        itemIndexDao.deleteAll(filter);
        // tickDao.deleteAll(filter);
    }

    public void addOrRemove(Long noteId, Long itemId, String attribute) {
        NoteItem noteItem = new NoteItem();
        Item item = itemDao.find(itemId);
        Iterator<NoteItem> it = noteItemDao.findAll(filter("noteId", noteId), filter("itemId", itemId)).iterator();
        if (it.hasNext()) {
            noteItem = it.next();
            if (it.hasNext()) {
                throw new IllegalStateException(String.format("Multiple instances exist for item %s in note %s",
                        item,
                        noteDao.find(noteId)));
            }
            if (StringUtil.isEmptyOrWhitespace(attribute)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Deleting noteItem with noteId = " + noteId + " and itemId = " + itemId);
                }
                noteItemDao.deleteAll(filter("noteId", noteId), filter("itemId", itemId));
                return;
            }
        }
        else {
            noteItem.setItemId(itemId);
            noteItem.setNoteId(noteId);
        }
        noteItem.setAttribute(attribute);
        noteItem.setText(item.getText());
        noteItemDao.save(noteItem);
    }
}
