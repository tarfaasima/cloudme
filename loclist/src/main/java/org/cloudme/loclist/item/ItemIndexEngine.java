package org.cloudme.loclist.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudme.loclist.model.ItemIndex;
import org.cloudme.loclist.model.Tick;

public class ItemIndexEngine {
    public Iterable<ItemIndex> createOrder(Long locationId, Iterable<Tick> ticks, Map<Long, ItemIndex> itemIndexMap) {
        if (itemIndexMap == null) {
            itemIndexMap = new HashMap<Long, ItemIndex>();
        }
        final List<ItemIndex> itemIndexs = new ArrayList<ItemIndex>(itemIndexMap.values());
        int lastIndex = -1;
        for (Tick tick : ticks) {
            Long itemId = tick.getItemId();
            ItemIndex itemIndex = itemIndexMap.get(itemId);
            if (itemIndex == null) {
                itemIndex = new ItemIndex();
                itemIndex.setItemId(itemId);
                itemIndex.setLocationId(locationId);
                itemIndexs.add(itemIndex);
            }
            Integer index = itemIndex.getIndex();
            if (index <= lastIndex) {
                itemIndex.setIndex(++lastIndex);
            }
            else {
                lastIndex = index;
            }
        }
        Collections.sort(itemIndexs);
        for (int i = 0, size = itemIndexs.size(); i < size; i++) {
            itemIndexs.get(i).setIndex(i);
        }
        return itemIndexs;
    }
}
