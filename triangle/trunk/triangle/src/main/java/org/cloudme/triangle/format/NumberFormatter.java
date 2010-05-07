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

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Formats {@link Number}s.
 * 
 * @see DecimalFormat
 * @author Moritz Petersen
 */
public class NumberFormatter implements Formatter<Number> {
    /**
     * The format instance used to format the value.
     */
    private NumberFormat format;

    /**
     * Sets the format pattern. If the pattern is null, a default
     * {@link NumberFormat} is used, otherwise a {@link DecimalFormat} is
     * created.
     */
    @Override
    public void setPattern(String pattern) {
        if (pattern == null) {
            format = NumberFormat.getInstance();
        }
        else {
            format = new DecimalFormat(pattern);
        }
    }

    /**
     * Formats a raw value to a printable {@link String}.
     * 
     * @param value
     *            The raw value.
     * @return The formatted {@link String}.
     */
    @Override
    public String format(Number value) {
        return format.format(value);
    }
}
