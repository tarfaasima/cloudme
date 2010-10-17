// Copyright 2010 Moritz Petersen
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.cloudme.loclist.item;

import java.util.Comparator;
import java.util.Map;

import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemOrder;

final class ItemInstanceComparator implements Comparator<ItemInstance> {
    private final Map<Long, ItemOrder> itemOrderMap;

    ItemInstanceComparator(Map<Long, ItemOrder> itemOrderMap) {
        this.itemOrderMap = itemOrderMap;
    }

    @Override
    public int compare(ItemInstance i1, ItemInstance i2) {
        ItemOrder o1 = itemOrderMap.get(i1.getItemId());
        ItemOrder o2 = itemOrderMap.get(i2.getItemId());
        return o1 == null ? o2 == null ? 0 : 1 : o2 == null ? -1 : o1.getIndex() - o2.getIndex();
    }
}