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
package org.cloudme.triangle.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ValidatorFactoryTest {
    @Test
    public void testValidatorTypeNotNull() {
        assertValidator(StringValidator.class, null, StringValidator.class);
    }

    @Test
    public void testValidatorTypeNull() {
        assertValidator(null, String.class, StringValidator.class);
    }

    @Test
    public void testNumericType() {
        assertValidator(null, byte.class, NumberValidator.class);
        assertValidator(null, short.class, NumberValidator.class);
        assertValidator(null, int.class, NumberValidator.class);
        assertValidator(null, long.class, NumberValidator.class);
        assertValidator(null, float.class, NumberValidator.class);
        assertValidator(null, double.class, NumberValidator.class);
        assertValidator(null, Integer.class, NumberValidator.class);
        assertValidator(null, Float.class, NumberValidator.class);
    }

    private static void assertValidator(Class<? extends Validator<?>> validatorType,
            Class<?> type,
            Class<? extends Validator<?>> expected) {
        final Validator<?> validator = ValidatorFactory
                .newInstance(validatorType, type);
        if (expected == null) {
            assertNull(validator);
        }
        else {
            assertEquals(expected, validator.getClass());
        }
    }
}
