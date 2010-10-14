package org.cloudme.loclist.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.cloudme.loclist.model.ItemOrder;
import org.cloudme.loclist.model.Tick;
import org.junit.Test;

public class ItemOrderEngineTest {
    @Test
    public void testCreateOrder() {
        ItemOrderEngine itemOrderEngine = new ItemOrderEngine();
        Iterable<ItemOrder> itemOrders;
        Map<Long, ItemOrder> itemOrderMap = null;
        Long locationId = 1L;
        itemOrders = itemOrderEngine.createOrder(locationId, ticks(2L, 1L), itemOrderMap);
        itemOrderMap = assertOrder(itemOrders, 2L, 1L);

        itemOrders = itemOrderEngine.createOrder(locationId, ticks(1L, 2L), itemOrderMap);
        itemOrderMap = assertOrder(itemOrders, 1L, 2L);

        itemOrders = itemOrderEngine.createOrder(locationId, ticks(3L, 4L), itemOrderMap);
        itemOrderMap = assertOrder(itemOrders, 1L, 3L, 2L, 4L);

        itemOrders = itemOrderEngine.createOrder(locationId, ticks(1L, 2L), itemOrderMap);
        itemOrderMap = assertOrder(itemOrders, 1L, 3L, 2L, 4L);

        itemOrders = itemOrderEngine.createOrder(locationId, ticks(2L, 3L), itemOrderMap);
        itemOrderMap = assertOrder(itemOrders, 1L, 2L, 3L, 4L);

        itemOrders = itemOrderEngine.createOrder(locationId, ticks(1L, 2L, 3L, 4L, 5L), itemOrderMap);
        itemOrderMap = assertOrder(itemOrders, 1L, 2L, 3L, 4L, 5L);

        itemOrders = itemOrderEngine.createOrder(locationId, ticks(2L, 1L), itemOrderMap);
        itemOrderMap = assertOrder(itemOrders, 2L, 1L, 3L, 4L, 5L);

        itemOrders = itemOrderEngine.createOrder(locationId, ticks(4L, 3L), itemOrderMap);
        itemOrderMap = assertOrder(itemOrders, 2L, 1L, 4L, 3L, 5L);
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

    private Map<Long, ItemOrder> assertOrder(Iterable<ItemOrder> itemOrders, Long... itemIds) {
        Map<Long, ItemOrder> itemOrderMap = new HashMap<Long, ItemOrder>();
        Iterator<ItemOrder> it = itemOrders.iterator();
        for (int i = 0; i < itemIds.length; i++) {
            assertTrue(it.hasNext());
            ItemOrder itemOrder = it.next();
            assertEquals(itemIds[i], itemOrder.getItemId());
            assertEquals(i, itemOrder.getIndex());

            itemOrderMap.put(itemOrder.getItemId(), itemOrder);
        }
        assertFalse(it.hasNext());
        return itemOrderMap;
    }
}
