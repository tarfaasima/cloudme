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
package org.cloudme.triangle.format;

import org.cloudme.triangle.Attribute;
import org.cloudme.util.ClassUtils;

/**
 * Creates instances of the {@link Formatter} for the given {@link Attribute}
 * type.
 * 
 * @author Moritz Petersen
 */
public class FormatterFactory {
    /**
     * Creates a new {@link Formatter} instance for the given type.
     * 
     * @param type
     *            The type for which the {@link Formatter} will be created.
     * @return The {@link Formatter} instance or null if no {@link Formatter}
     *         exists for the given type.
     */
    public static Formatter<?> newInstanceFor(Class<?> type) {
        if (ClassUtils.isNumber(type)) {
            return new NumberFormatter();
        }
        return null;
    }
}
