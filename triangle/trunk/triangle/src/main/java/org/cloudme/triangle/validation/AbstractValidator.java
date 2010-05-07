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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Dummy base class for implementing {@link Validator}s. All methods throw an
 * {@link UnsupportedOperationException} and need to be overridden by inheriting
 * classes.
 * 
 * @author Moritz Petersen
 */
public abstract class AbstractValidator<T> implements Validator<T> {
    /**
     * Used to perform one validation {@link Check}. The
     * {@link AbstractValidator} can have multiple validation {@link Check}s.
     * 
     * @author Moritz Petersen
     * @param <T>
     *            The type of the value that is validated.
     */
    protected interface Check<T> {
        boolean perform(T value);
    }

    /**
     * All validation {@link Check}s that are performed by this
     * {@link Validator}.
     */
    private final Collection<Check<T>> checks = new ArrayList<Check<T>>();

    /**
     * Adds a {@link Check} to this {@link Validator}.
     * 
     * @param check
     *            The {@link Check} that is added.
     */
    protected void addCheck(Check<T> check) {
        checks.add(check);
    }

    /**
     * Empty implementation.
     */
    @Override
    public void setMask(String mask) {
    }

    /**
     * Empty implementation.
     */
    @Override
    public void setMax(Double max) {
    }

    /**
     * Empty implementation.
     */
    @Override
    public void setMin(Double min) {
    }

    /**
     * Validates the given value.
     */
    @Override
    public void validate(T value) {
        for (final Check<T> check : checks) {
            if (!check.perform(value)) {
                throw new ValidationException();
            }
        }
    }
}
