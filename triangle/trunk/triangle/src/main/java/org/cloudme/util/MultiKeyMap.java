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
package org.cloudme.util;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple map, not following the {@link Map} interface, but allowing to store
 * a value with multiple different keys. The map is read-only.
 * 
 * @author Moritz Petersen
 * @param <V>
 *            the type of the value.
 */
public class MultiKeyMap<V> {
    /**
     * The map that stores the keys and values.
     */
    private final Map<Object, V> map = new HashMap<Object, V>();

    /**
     * Puts the value with multiple keys.
     * 
     * @param value
     *            The value
     * @param keys
     *            The keys
     */
    public void put(V value, Object... keys) {
        for (final Object key : keys) {
            map.put(key, value);
        }
    }

    /**
     * Returns the value for the given key.
     * 
     * @param key
     *            The key
     * @return the value
     */
    public V get(Object key) {
        return map.get(key);
    }
}
