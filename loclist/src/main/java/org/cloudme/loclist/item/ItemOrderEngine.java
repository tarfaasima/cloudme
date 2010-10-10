package org.cloudme.loclist.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudme.loclist.model.ItemOrder;
import org.cloudme.loclist.model.Tick;

public class ItemOrderEngine {
    public Iterable<ItemOrder> createOrder(Iterable<Tick> ticks, Map<Long, ItemOrder> itemOrderMap) {
        if (itemOrderMap == null) {
            itemOrderMap = new HashMap<Long, ItemOrder>();
        }
        final List<ItemOrder> itemOrders = new ArrayList<ItemOrder>(itemOrderMap.values());
        int lastIndex = -1;
        for (Tick tick : ticks) {
            Long itemId = tick.getItemId();
            ItemOrder itemOrder = itemOrderMap.get(itemId);
            if (itemOrder == null) {
                itemOrder = new ItemOrder();
                itemOrder.setItemId(itemId);
                itemOrders.add(itemOrder);
            }
            Integer index = itemOrder.getIndex();
            if (index <= lastIndex) {
                itemOrder.setIndex(++lastIndex);
            }
            else {
                lastIndex = index;
            }
        }
        Collections.sort(itemOrders);
        for (int i = 0, size = itemOrders.size(); i < size; i++) {
            itemOrders.get(i).setIndex(i);
        }
        return itemOrders;
    }
}
