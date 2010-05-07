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

import org.cloudme.util.ClassUtils;

/**
 * Creates instances of the {@link Validator} for the given validatorType or, if
 * the validatorType is null, it tries to instantiate a {@link Validator} that
 * matches the given field type.
 * 
 * @author Moritz Petersen
 */
public class ValidatorFactory {
    /**
     * Creates instances of the {@link Validator} for the given validatorType
     * or, if the validatorType is null, it tries to instantiate a
     * {@link Validator} that matches the given field type.
     * 
     * @param validatorType
     *            The type of the {@link Validator} that should be instanciated.
     *            If that type is null or instatiation fails, a
     *            {@link Validator} matching the field type is created.
     * @param type
     *            The field type that needs to be validated.
     * @return A new {@link Validator} or null if instantiation was not
     *         possible.
     */
    public static Validator<?> newInstanceFor(Class<? extends Validator<?>> validatorType, Class<?> type) {
        if (validatorType != null) {
            try {
                return validatorType.newInstance();
            }
            catch (final InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (final IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (type == null) {
            return null;
        }
        if (ClassUtils.isString(type)) {
            return new StringValidator();
        }
        if (ClassUtils.isNumber(type)) {
            return new NumberValidator();
        }
        return null;
    }
}
