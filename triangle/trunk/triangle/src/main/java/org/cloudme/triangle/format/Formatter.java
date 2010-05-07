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

/**
 * Formats a raw value to a printable {@link String}.
 * 
 * @see FormatterFactory
 * @author Moritz Petersen
 * @param <T>
 *            The type of the raw value.
 */
public interface Formatter<T> {
    /**
     * The {@link Formatter} pattern.
     * 
     * @param pattern
     *            The {@link Formatter} pattern.
     */
    void setPattern(String pattern);

    /**
     * Formats a raw value to a printable {@link String}.
     * 
     * @param value
     *            The raw value.
     * @return The formatted {@link String}.
     */
    String format(T value);
}
