package org.cloudme.loclist.item;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;

import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemIndex;
import org.cloudme.loclist.model.Location;
import org.junit.Test;

public class ItemIndexEngineTest {
    private static final long LOCATION_ID = 10001L;
    private static final long ITEM_ID = 10002L;
    private static final long TS_1 = createTimestamp("21.09.2010");
    private static final long TS_2 = createTimestamp("22.09.2010");
    private final ItemIndexEngine engine = new ItemIndexEngine();

    @Test
    public void testCreateOrder() {
        assertIndex(0, null, 0, TS_1, null, 0, TS_1);
        assertIndex(0, 0L, 0, TS_1, 0L, 0, TS_1);
        assertIndex(1, null, 0, TS_1, 0L, 0, TS_1);
        assertIndex(1, 1L, 1, TS_1, 0L, 0, TS_1);
        assertIndex(2, 1L, 0, TS_1, 0L, 1, TS_1);
        assertIndex(0, 1L, 0, TS_2, 0L, 1, TS_1);
    }

    private void assertIndex(int expected,
            Long itemId,
            int itemIndex,
            long itemTimestamp,
            Long lastItemId,
            int lastItemIndex,
            long lastItemTimestamp) {
        int index = engine.update(new Location(LOCATION_ID),
                new Item(ITEM_ID),
                itemTimestamp,
                createItemIndex(itemId, itemIndex, itemTimestamp),
                createItemIndex(lastItemId, lastItemIndex, lastItemTimestamp)).getIndex();
        assertEquals(expected, index);
    }

    private ItemIndex createItemIndex(Long itemId, int index, long timestamp) {
        if (itemId == null) {
            return null;
        }
        ItemIndex itemIndex = new ItemIndex();
        itemIndex.setItemId(itemId);
        itemIndex.setIndex(index);
        itemIndex.setLastUpdate(timestamp);
        return itemIndex;
    }

    private static long createTimestamp(String dateStr) {
        try {
            return DateFormat.getDateInstance().parse(dateStr).getTime();
        }
        catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
