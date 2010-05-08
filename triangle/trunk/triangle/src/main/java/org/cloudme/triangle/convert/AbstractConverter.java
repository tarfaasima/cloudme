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

import java.text.Format;
import java.text.ParseException;

/**
 * Abstract base class to simplify implementation of custom {@link Converter}s
 * based on {@link Format}.
 * 
 * @author Moritz Petersen
 * 
 * @param <T>
 *            Type of the converted value.
 */
public abstract class AbstractConverter<T> implements Converter<T> {
    /**
     * {@link Format} used to convert.
     */
    private Format format;

    @Override
    @SuppressWarnings( "unchecked" )
    public T convert(String str) {
        try {
            return (T) format.parseObject(str);
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setPattern(String pattern) {
        format = createFormat(pattern);
    }

    /**
     * Creates the {@link Format} instance for this {@link Converter}.
     * 
     * @param pattern
     *            The pattern which is used to initialize the {@link Format};
     * @return The {@link Converter} instance.
     */
    protected abstract Format createFormat(String pattern);
}
