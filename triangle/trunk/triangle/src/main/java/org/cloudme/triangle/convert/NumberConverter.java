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
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Converts {@link Number}s.
 * 
 * @see DecimalFormat
 * @author Moritz Petersen
 */
public class NumberConverter implements Converter<Number> {
    /**
     * The format instance used to parse the value.
     */
    private NumberFormat format;

    /**
     * Sets the format pattern.
     */
    @Override
    public void setPattern(String pattern) {
        format = new DecimalFormat(pattern);
    }

    /**
     * Converts the given {@link String} into a {@link Number}.
     */
    @Override
    public Number convert(String str) {
        try {
            return format.parse(str);
        }
        catch (final ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
