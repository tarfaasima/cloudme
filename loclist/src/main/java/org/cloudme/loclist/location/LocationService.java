package org.cloudme.loclist.location;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.loclist.item.Item;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.note.NoteItem;
import org.cloudme.loclist.note.NoteService;

import com.google.inject.Inject;

public class LocationService {
    private static final Log LOG = LogFactory.getLog(LocationService.class);
    private static final String THUMBNAIL_ADDRESS = "http://maps.google.com/maps/api/staticmap?center=%1$s,%2$s&zoom=15&size=300x150&maptype=roadmap&markers=color:red%%7C%1$s,%2$s&sensor=false";
    @Inject
    private LocationDao locationDao;
    /**
     * The radius in kilometers of tolerance to map a checkin to an existing
     * location within this radius.
     */
    private double radius = 0.5d;
    private final ItemIndexEngine engine = new ItemIndexEngine();
    @Inject
    private ItemIndexDao itemIndexDao;
    @Inject
    private NoteService noteService;
    @Inject
    private ItemService itemService;

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

    public Collection<Location> findAllLocations() {
        Map<Long, Item> itemMap = null;
        List<Location> locations = locationDao.listAll();
        for (Location location : locations) {
            List<ItemIndex> itemIndexs = itemIndexDao.listBy(location);
            location.setItemIndexs(itemIndexs);
            for (ItemIndex itemIndex : itemIndexs) {
                if (itemIndex.getText() == null) {
                    if (itemMap == null) {
                        itemMap = new HashMap<Long, Item>();
                        Iterable<Item> items = itemService.findAll();
                        for (Item item : items) {
                            itemMap.put(item.getId(), item);
                        }
                    }
                    itemIndex.setText(itemMap.get(itemIndex.getItemId()).getText());
                    itemIndexDao.put(itemIndex);
                }
            }
        }
        return locations;
    }

    public Location findWithThumbnail(Long id) {
        Location location = locationDao.find(id);
        if (location.getThumbnailBytes() == null) {
            String address = String.format(THUMBNAIL_ADDRESS, location.getLatitude(), location.getLongitude());
            try {
                URL url = new URL(address);
                InputStream in = null;
                try {
                    in = url.openStream();
                    location.setThumbnailBytes(IOUtils.toByteArray(in));
                    locationDao.put(location);
                }
                catch (IOException e) {
                    LOG.warn("Unable to retrieve thumbnail " + address, e);
                }
                finally {
                    IOUtils.closeQuietly(in);
                }
            }
            catch (MalformedURLException e) {
                throw new IllegalStateException("Malformed URL: " + address, e);
            }
        }
        return location;
    }

    public void delete(Long id) {
        locationDao.delete(id);
        itemIndexDao.deleteByLocationId(id);
    }

    public Location findWithItemIndexs(Long id) {
        Location location = locationDao.find(id);
        List<ItemIndex> itemIndexs = itemIndexDao.listBy(location);
        location.setItemIndexs(itemIndexs);
        return location;
    }
}
