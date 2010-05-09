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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
     * @param pattern
     *            The pattern that is used to convert the raw type.
     * @return The {@link Converter} instance or null if no {@link Converter}
     *         exists for the given type.
     */
    public static Converter<?> newInstance(Class<?> type, String pattern) {
        if (ClassUtils.isNumber(type)) {
            return new FormatConverter<Number>(pattern == null ? NumberFormat
                    .getNumberInstance() : new DecimalFormat(pattern));
        }
        if (ClassUtils.isDate(type)) {
            return new FormatConverter<Date>(pattern == null ? DateFormat
                    .getDateInstance() : new SimpleDateFormat(pattern));
        }
        if (ClassUtils.isBoolean(type)) {
            return new ToStringConverter() {
                @Override
                public Boolean convert(String str) {
                    return Boolean.valueOf(str);
                }
            };
        }
        return null;
    }
}
