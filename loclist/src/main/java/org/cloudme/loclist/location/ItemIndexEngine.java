package org.cloudme.loclist.location;

import org.cloudme.loclist.item.Item;
import org.cloudme.loclist.item.ItemService;

/**
 * Component that updates the index value of {@link ItemIndex}.
 * 
 * @author Moritz Petersen
 * @see ItemService#tick(Long, Long, long)
 */
class ItemIndexEngine {
    /**
     * Hours.
     */
    private static final int HOUR_MS = 60 * 60 * 1000;
    /**
     * Timeout is one day.
     */
    private static final long TIMEOUT = 24 * HOUR_MS;

    /**
     * Updates the index value of the given <tt>itemIndex</tt>. If the
     * <tt>itemIndex</tt> is <tt>null</tt> a new {@link ItemIndex} is created.
     * This method is called when an item is ticked in a note to recreate the
     * order of items.
     * 
     * @param location
     *            The {@link Location}
     * @param item
     *            The {@link Item}
     * @param timestamp
     *            The timestamp of the current tick in milliseconds (see
     *            {@link System#currentTimeMillis()})
     * @param itemIndex
     *            The {@link ItemIndex} corresponding the tick. If <tt>null</tt>
     *            a new {@link ItemIndex} will be created based on
     *            <tt>itemId</tt>, <tt>locationId</tt> and <tt>timestamp</tt>
     * @param lastItemIndex
     *            The previous {@link ItemIndex}. If <tt>null</tt> the
     *            application actually starts from scratch. This
     *            {@link ItemIndex} is used to calculate the index value
     * 
     * @return The updated or created {@link ItemIndex}
     */
    public ItemIndex update(Location location, Item item, long timestamp, ItemIndex itemIndex, ItemIndex lastItemIndex) {
        if (itemIndex == null) {
            itemIndex = new ItemIndex();
            itemIndex.setItemId(item.getId());
            itemIndex.setLocationId(location.getId());
            itemIndex.setLastUpdate(timestamp);
        }

        if (lastItemIndex != null) {
            if (itemIndex.getItemId().equals(lastItemIndex.getItemId())) {
                return itemIndex;
            }

            int lastIndex = lastItemIndex.getIndex();
            if (isOutdated(itemIndex, lastItemIndex)) {
                return itemIndex;
            }
            if (isAlreadyLower(itemIndex.getIndex(), lastIndex)) {
                return itemIndex;
            }
            itemIndex.setIndex(lastIndex + 1);
        }
        else {
            itemIndex.setIndex(0);
        }
        return itemIndex;
    }

    /**
     * Checks if the last tick is already outdated (currently by comparing the
     * {@link ItemIndex#getLastUpdate()} timestamps checking if they are longer
     * than 24h apart).
     * 
     * @param itemIndex
     *            The current {@link ItemIndex}
     * @param lastItemIndex
     *            The previous {@link ItemIndex}
     * @return <tt>true</tt> if the last tick is already outdated, otherwise
     *         <tt>false</tt>.
     */
    private boolean isOutdated(ItemIndex itemIndex, ItemIndex lastItemIndex) {
        return itemIndex.getLastUpdate() - lastItemIndex.getLastUpdate() >= TIMEOUT;
    }

    /**
     * Checks if the current index is already lower than the previous index.
     * 
     * @param index
     *            The current index
     * @param lastIndex
     *            The previous index
     * @return <tt>true</tt> if the current index is already lower than the
     *         previous index, otherwise <tt>false</tt>.
     */
    private boolean isAlreadyLower(int index, int lastIndex) {
        return index > lastIndex;
    }
}
