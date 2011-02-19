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

import org.cloudme.loclist.model.NoteItem;
import org.cloudme.loclist.model.ItemIndex;

final class NoteItemComparator implements Comparator<NoteItem> {
    private final Map<Long, ItemIndex> itemIndexMap;

    NoteItemComparator(Map<Long, ItemIndex> itemIndexMap) {
        this.itemIndexMap = itemIndexMap;
    }

    @Override
    public int compare(NoteItem i1, NoteItem i2) {
        ItemIndex o1 = itemIndexMap.get(i1.getItemId());
        ItemIndex o2 = itemIndexMap.get(i2.getItemId());
        return o1 == null ? o2 == null ? 0 : 1 : o2 == null ? -1 : o1.getIndex() - o2.getIndex();
    }
}