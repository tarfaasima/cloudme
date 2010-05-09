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

/**
 * Validates a {@link Number} based on the given configuration. The
 * {@link #setMask(String)} method is not supported.
 * 
 * @author Moritz Petersen
 */
public class NumberValidator extends AbstractValidator<Number> {
    /**
     * The maximum number (inclusive). Optional.
     */
    @Override
    public void setMax(final double max) {
        addCheck(new Check<Number>() {
            public boolean perform(Number value) {
                return value.doubleValue() <= max;
            }
        });
    }

    /**
     * The minimum number (inclusive). Optional.
     */
    @Override
    public void setMin(final double min) {
        addCheck(new Check<Number>() {
            public boolean perform(Number value) {
                return value.doubleValue() >= min;
            }
        });
    }
}
