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
 * Interface for {@link Validator}s. See the specific {@link Validator}
 * implementations for further information.
 * 
 * @author Moritz Petersen
 */
public interface Validator<T> {
    /**
     * A mask that must be matched by the given value. Optional.
     * 
     * @param mask
     *            A mask that must be matched by the given value. Optional.
     */
    void setMask(String mask);

    /**
     * The maximum value of the given value. Optional.
     * 
     * @param max
     *            The maximum value of the given value. Optional.
     */
    void setMax(Double max);

    /**
     * The minimum value of the given value. Optional.
     * 
     * @param min
     *            The minimum value of the given value. Optional.
     */
    void setMin(Double min);

    /**
     * Validates the value.
     * 
     * @param value
     *            The value that is validated.
     * @throws ValidationException
     *             on error.
     */
    void validate(T value);
}
