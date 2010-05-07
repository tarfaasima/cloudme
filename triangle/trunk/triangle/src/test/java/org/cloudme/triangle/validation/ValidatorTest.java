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

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public abstract class ValidatorTest<T> {
    protected Validator<T> validator;
    private final String mask;
    private final Double max;
    private final Double min;
    private final T invalidMax;
    private final T invalidMin;
    private final T valid;
    private final T invalidMask;

    public ValidatorTest() {
        this(null, null, null, null, null, null, null);
    }

    public ValidatorTest(String mask, Double max, Double min, T invalidMask, T invalidMax, T invalidMin, T valid) {
        this.mask = mask;
        this.max = max;
        this.min = min;
        this.invalidMask = invalidMask;
        this.invalidMax = invalidMax;
        this.invalidMin = invalidMin;
        this.valid = valid;

    }

    protected abstract Validator<T> createValidator();

    @Before
    public void setUp() {
        validator = createValidator();
    }

    @Test
    public void testValidateMask() {
        if (mask == null) {
            return;
        }
        validator.setMask(mask);
        assertValid(valid);
    }

    @Test
    public void testInvalidMask() {
        if (invalidMask == null) {
            return;
        }
        validator.setMask(mask);
        assertNotValid(invalidMask);
    }

    @Test
    public void testInvalidMax() {
        if (max == null) {
            return;
        }
        validator.setMax(max);
        assertNotValid(invalidMax);
    }

    @Test
    public void testInvalidMin() {
        if (min == null) {
            return;
        }
        validator.setMin(min);
        assertNotValid(invalidMin);
    }

    @Test
    public void testInValidRange() {
        if (max == null || min == null) {
            return;
        }
        validator.setMax(max);
        validator.setMin(min);
        assertValid(valid);
    }

    void assertValid(T value) {
        validator.validate(value);
    }

    void assertNotValid(T value) {
        try {
            validator.validate(value);
            fail();
        }
        catch (final ValidationException e) {
            // expected
        }
    }
}
