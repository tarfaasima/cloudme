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

/**
 * Converts a {@link String} to a raw value and vice versa.
 * 
 * @see ConverterFactory
 * @author Moritz Petersen
 * @param <T>
 *            The type of the raw value.
 */
public interface Converter<T> {
    /**
     * Identifies that no pattern will be used.
     */
    String NO_PATTERN = "";

    /**
     * The pattern that is used to parse the {@link String}.
     * 
     * @param pattern
     *            The pattern that is used to parse the {@link String}.
     */
    void setPattern(String pattern);

    /**
     * Converts a {@link String} to a raw value.
     * 
     * @param str
     *            The formatted string, that is parsed.
     * @return The raw value.
     */
    T convert(String str);

    /**
     * Formats a raw value to a {@link String}.
     * 
     * @param value
     *            The raw value.
     * @return The formatted {@link String}.
     */
    String format(T value);
}
