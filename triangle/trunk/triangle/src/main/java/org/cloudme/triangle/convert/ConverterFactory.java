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
package org.cloudme.triangle.convert;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cloudme.triangle.Attribute;
import org.cloudme.util.ClassUtils;

/**
 * Creates instances of the {@link Converter} for the given {@link Attribute}
 * type.
 * 
 * @author Moritz Petersen
 */
public class ConverterFactory {
    /**
     * Creates a new {@link Converter} instance for the given type.
     * 
     * @param type
     *            The type for which the {@link Converter} will be created.
     * @return The {@link Converter} instance or null if no {@link Converter}
     *         exists for the given type.
     */
    public static Converter<?> newInstanceFor(Class<?> type) {
        if (ClassUtils.isNumber(type)) {
            return new AbstractConverter<Number>() {
                @Override
                protected Format createFormat(String pattern) {
                    return new DecimalFormat(pattern);
                }
            };
        }
        if (ClassUtils.isDate(type)) {
            return new AbstractConverter<Date>() {
                @Override
                protected Format createFormat(String pattern) {
                    return new SimpleDateFormat(pattern);
                }
            };
        }
        return null;
    }

}
