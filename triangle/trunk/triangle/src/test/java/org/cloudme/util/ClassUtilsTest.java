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
package org.cloudme.util;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class ClassUtilsTest {
    @Test
    public void testIsString() {
        assertTrue(ClassUtils.isString(String.class));
    }

    @Test
    public void testIsNumber() {
        assertTrue(ClassUtils.isNumber(int.class));
    }

    @Test
    public void testIsDate() {
        assertTrue(ClassUtils.isDate(Date.class));
    }
}
