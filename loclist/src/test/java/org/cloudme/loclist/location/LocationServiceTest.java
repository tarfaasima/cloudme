package org.cloudme.loclist.location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.cloudme.loclist.note.NoteItem;
import org.cloudme.loclist.note.NoteService;
import org.cloudme.loclist.test.BaseTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

public class LocationServiceTest extends BaseTestCase {
    private static final float LONDON_LAT = 51.500152f;
    private static final float LONDON_LON = -0.126236f;
    private static final float MANCHESTER_LAT = 53.480712f;
    private static final float MANCHESTER_LON = -2.234376f;
    @Inject
    private LocationService locationService;
    @Inject
    private LocationDao locationDao;
    @Inject
    private ItemIndexDao itemIndexDao;
    @Inject
    private NoteService noteService;

    @Before
    public void generateTestData() {
        createItems("Milk", "Cheese", "Tea", "Bread", "Sugar", "Update status report");
        createNote("Shopping List", "Milk", "Cheese", "Tea", "Bread", "Sugar");
        createNote("My Todo List", "Update status report");

        assertEquals(2, noteService.listAll().size());
    }

    @Test
    public void testCheckin() {
        assertEquals(0, locationDao.listAll().size());
        Location manchester = locationService.checkin(MANCHESTER_LAT, MANCHESTER_LON);
        assertEquals(1, locationDao.listAll().size());

        locationService.setRadius(300);
        Location thisIsNotLondon = locationService.checkin(LONDON_LAT, LONDON_LON);
        assertEquals(1, locationDao.listAll().size());
        assertEquals(manchester.getId(), thisIsNotLondon.getId());

        locationService.setRadius(100);
        Location london = locationService.checkin(LONDON_LAT, LONDON_LON);
        assertEquals(2, locationDao.listAll().size());
        assertEquals(LONDON_LAT, london.getGeoPt().getLatitude(), 0.001);
    }

    @Test
    public void testTick() {
        Location manchester = locationService.checkin(MANCHESTER_LAT, MANCHESTER_LON);
        assertEquals(0, list(itemIndexDao.findAll()).size());
        locationService.tick(manchester, noteItem("Milk"), System.currentTimeMillis());
        assertEquals(1, list(itemIndexDao.findAll()).size());
        locationService.tick(manchester, noteItem("Tea"), System.currentTimeMillis());
        assertEquals(2, list(itemIndexDao.findAll()).size());
        locationService.tick(manchester, noteItem("Bread"), System.currentTimeMillis());
        assertEquals(3, list(itemIndexDao.findAll()).size());
    }

    @Test
    public void testSortNoteItems() {
        long ts = System.currentTimeMillis();

        Location manchester = locationService.checkin(MANCHESTER_LAT, MANCHESTER_LON);
        List<NoteItem> noteItems = null;

        noteItems = noteService.getNoteItems(note("Shopping List"));
        locationService.sortNoteItems(manchester, noteItems);
        assertItemOrder(noteItems, "Bread", "Cheese", "Milk", "Sugar", "Tea");

        locationService.tick(manchester, noteItem("Milk"), ts++);
        locationService.tick(manchester, noteItem("Tea"), ts++);
        locationService.tick(manchester, noteItem("Bread"), ts++);

        noteItems = noteService.getNoteItems(note("Shopping List"));
        locationService.sortNoteItems(manchester, noteItems);
        assertItemOrder(noteItems, "Cheese", "Sugar", "Milk", "Tea", "Bread");
    }

    private void assertItemOrder(List<NoteItem> actualNoteItems, String... expectedTexts) {
        assertEquals(expectedTexts.length, actualNoteItems.size());
        for (int i = 0, max = expectedTexts.length; i < max; i++) {
            assertEquals(expectedTexts[i], actualNoteItems.get(i).getText());
        }
    }

    @Test
    public void testDeleteByItemId() {
        assertEquals(0, itemIndexDao.listAll().size());

        Location manchester = locationService.checkin(MANCHESTER_LAT, MANCHESTER_LON);
        locationService.tick(manchester, noteItem("Milk"), System.currentTimeMillis());
        assertEquals(1, itemIndexDao.listAll().size());

        locationService.deleteByItemId(item("Milk").getId());
        assertEquals(0, itemIndexDao.listAll().size());
    }

    @Test
    public void testFindAllLocations() {
        Location man = locationService.checkin(MANCHESTER_LAT, MANCHESTER_LON);
        locationService.tick(man, noteItem("Milk"), System.currentTimeMillis());
        locationService.checkin(LONDON_LAT, LONDON_LON);

        Collection<Location> locations = locationService.findAllLocations();
        assertEquals(2, locations.size());
        Iterator<Location> it = locations.iterator();
        Location l1 = it.next();
        assertEquals(MANCHESTER_LAT, l1.getLatitude(), 0.00001d);
        assertEquals("Milk", l1.getItemIndexs().iterator().next().getText());
        assertTrue(it.next().getItemIndexs().isEmpty());
        assertFalse(it.hasNext());
    }

    @Test
    public void testDelete() {
        Location man = locationService.checkin(MANCHESTER_LAT, MANCHESTER_LON);
        locationService.tick(man, noteItem("Milk"), System.currentTimeMillis());
        assertEquals(1, itemIndexDao.listBy(man).size());
        assertEquals(1, locationDao.listAll().size());

        locationService.delete(man.getId());
        assertEquals(0, itemIndexDao.listBy(man).size());
        assertEquals(0, locationDao.listAll().size());
    }

    private <T> List<T> list(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<T>();
        for (T t : iterable) {
            list.add(t);
        }
        return list;
    }

}
