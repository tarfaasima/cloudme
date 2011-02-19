package org.cloudme.loclist.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.cloudme.loclist.model.ItemIndex;
import org.cloudme.loclist.model.Tick;
import org.junit.Test;

public class ItemIndexEngineTest {
    @Test
    public void testCreateOrder() {
        ItemIndexEngine itemIndexEngine = new ItemIndexEngine();
        Iterable<ItemIndex> itemIndexs;
        Map<Long, ItemIndex> itemIndexMap = null;
        Long locationId = 1L;
        itemIndexs = itemIndexEngine.createOrder(locationId, ticks(2L, 1L), itemIndexMap);
        itemIndexMap = assertOrder(itemIndexs, 2L, 1L);

        itemIndexs = itemIndexEngine.createOrder(locationId, ticks(1L, 2L), itemIndexMap);
        itemIndexMap = assertOrder(itemIndexs, 1L, 2L);

        itemIndexs = itemIndexEngine.createOrder(locationId, ticks(3L, 4L), itemIndexMap);
        itemIndexMap = assertOrder(itemIndexs, 1L, 3L, 2L, 4L);

        itemIndexs = itemIndexEngine.createOrder(locationId, ticks(1L, 2L), itemIndexMap);
        itemIndexMap = assertOrder(itemIndexs, 1L, 3L, 2L, 4L);

        itemIndexs = itemIndexEngine.createOrder(locationId, ticks(2L, 3L), itemIndexMap);
        itemIndexMap = assertOrder(itemIndexs, 1L, 2L, 3L, 4L);

        itemIndexs = itemIndexEngine.createOrder(locationId, ticks(1L, 2L, 3L, 4L, 5L), itemIndexMap);
        itemIndexMap = assertOrder(itemIndexs, 1L, 2L, 3L, 4L, 5L);

        itemIndexs = itemIndexEngine.createOrder(locationId, ticks(2L, 1L), itemIndexMap);
        itemIndexMap = assertOrder(itemIndexs, 2L, 1L, 3L, 4L, 5L);

        itemIndexs = itemIndexEngine.createOrder(locationId, ticks(4L, 3L), itemIndexMap);
        itemIndexMap = assertOrder(itemIndexs, 2L, 1L, 4L, 3L, 5L);
    }

    private Iterable<Tick> ticks(Long... itemIds) {
        ArrayList<Tick> ticks = new ArrayList<Tick>();
        for (Long itemId : itemIds) {
            Tick tick = new Tick();
            tick.setItemId(itemId);
            ticks.add(tick);
        }
        return ticks;
    }

    private Map<Long, ItemIndex> assertOrder(Iterable<ItemIndex> itemIndexs, Long... itemIds) {
        Map<Long, ItemIndex> itemIndexMap = new HashMap<Long, ItemIndex>();
        Iterator<ItemIndex> it = itemIndexs.iterator();
        for (int i = 0; i < itemIds.length; i++) {
            assertTrue(it.hasNext());
            ItemIndex itemIndex = it.next();
            assertEquals(itemIds[i], itemIndex.getItemId());
            assertEquals(i, itemIndex.getIndex());

            itemIndexMap.put(itemIndex.getItemId(), itemIndex);
        }
        assertFalse(it.hasNext());
        return itemIndexMap;
    }
}
