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
 * Validates a {@link String} based on the given configuration.
 * 
 * @author Moritz Petersen
 */
public class StringValidator extends AbstractValidator<String> {
    /**
     * Regular expression that must be matched by the given {@link String}.
     */
    @Override
    public void setMask(final String mask) {
        if (mask != null) {
            addCheck(new Check<String>() {
                public boolean perform(String value) {
                    return value.matches(mask);
                }
            });
        }
    }

    /**
     * Maximum (inclusive) length of the {@link String}
     */
    @Override
    public void setMax(final Double max) {
        if (max != null) {
            addCheck(new Check<String>() {
                public boolean perform(String value) {
                    return value.length() <= max;
                }
            });
        }
    }

    /**
     * Minimum (inclusive) length of the {@link String}.
     */
    @Override
    public void setMin(final Double min) {
        if (min != null) {
            addCheck(new Check<String>() {
                public boolean perform(String value) {
                    return value.length() >= min;
                }
            });
        }
    }
}
