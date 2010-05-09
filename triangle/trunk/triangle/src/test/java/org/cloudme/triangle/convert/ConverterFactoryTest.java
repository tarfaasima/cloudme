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

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

public class ConverterFactoryTest {
    @Test
    @SuppressWarnings( "unchecked" )
    public void testFormatNumber() {
        Locale.setDefault(Locale.US);
        final Converter<Number> Converter = (Converter<Number>) ConverterFactory
                .newInstance(Double.class, "0.0");
        assertEquals("82.0", Converter.format(82));
    }
}
