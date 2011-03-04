package org.cloudme.loclist.location;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudme.loclist.item.Item;
import org.cloudme.loclist.note.NoteItem;
import org.cloudme.loclist.note.NoteService;

import com.google.inject.Inject;

public class LocationService {
    @Inject
    private LocationDao locationDao;
    /**
     * The radius in kilometers of tolerance to map a checkin to an existing
     * location within this radius.
     */
    private double radius = 0.05d;
    private final ItemIndexEngine engine = new ItemIndexEngine();
    @Inject
    private ItemIndexDao itemIndexDao;
    @Inject
    private NoteService noteService;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Checks in at the given geo location. If a location with this coordinates
     * or in the distance no longer than the {@link #radius} already exists, the
     * existing location is returned.
     * 
     * @param latitude
     *            The latitude of the geo location.
     * @param longitude
     *            The longitude of the geo location.
     * @return The nearest existing {@link Location} within a defined
     *         {@link #radius} or a new {@link Location}.
     */
    public Location checkin(float latitude, float longitude) {
        Location location = null;
        double dMin = Double.MAX_VALUE;
        for (Location tmp : locationDao.findAll()) {
            double d = distance(latitude, longitude, tmp.getLatitude(), tmp.getLongitude());
            if (d < radius && d < dMin) {
                location = tmp;
                dMin = d;
            }
        }
        if (location == null) {
            location = new Location(latitude, longitude);
            locationDao.put(location);
        }
        return location;
    }

    /**
     * http://www.movable-type.co.uk/scripts/latlong.html
     * 
     * @param lat1
     *            Latitude of first location.
     * @param lon1
     *            Longitude of first location.
     * @param lat2
     *            Latitude of second location.
     * @param lon2
     *            Longitude of second location.
     * @return The distance between two locations in km.
     */
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        double angle = Math.acos(Math.sin(lat1)
                * Math.sin(lat2)
                + Math.cos(lat1)
                * Math.cos(lat2)
                * Math.cos(lon1 - lon2));
        return 6371.0f * angle;
    }

    /**
     * Registers a tick for the given {@link Location} and {@link NoteItem} and
     * triggers index update.
     * 
     * @param location
     *            The {@link Location}
     * @param noteItem
     *            The {@link NoteItem}
     * @param timestamp TODO
     */
    public void tick(Location location, NoteItem noteItem, long timestamp) {
        if (noteItem.isTicked()) {
            return;
        }
        noteItem.setTicked(true);
        noteService.put(noteItem);

        Item item = new Item(noteItem.getItemId());
        ItemIndex itemIndex = itemIndexDao.findBy(location, item);
        ItemIndex lastItemIndex = itemIndexDao.findLastBy(location);
        itemIndex = engine.update(location, item, timestamp, itemIndex, lastItemIndex);
        itemIndexDao.put(itemIndex);
    }

    public void sortNoteItems(Location location, List<NoteItem> noteItems) {
        Iterable<ItemIndex> itemIndexs = itemIndexDao.findBy(location);
        final Map<Long, ItemIndex> itemIndexMap = new HashMap<Long, ItemIndex>();
        for (ItemIndex itemIndex : itemIndexs) {
            itemIndexMap.put(itemIndex.getItemId(), itemIndex);
        }
        Collections.sort(noteItems, new NoteItemComparator(itemIndexMap));
    }

    public void deleteByItemId(Long itemId) {
        itemIndexDao.deleteByItemId(itemId);
    }
}
