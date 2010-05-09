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

import org.cloudme.triangle.annotation.Validate.NoValidator;
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
     *            {@link Validator} matching the field type is created. If
     *            {@link NoValidator#class} this parameter will be ignored.
     * @param fieldType
     *            The field type that needs to be validated.
     * @param mask
     *            The mask that will be checked. If {@link Validator#NO_MASK} or
     *            <code>null</code>, the parameter will be ignored.
     * @param max
     *            The maximum value that will be checked. If
     *            {@link Validator#NO_VALUE}, this parameter will be ignored.
     * @param min
     *            The minimum value that will be checked. If
     *            {@link Validator#NO_VALUE}, this parameter will be ignored
     * @return A new {@link Validator} or null if instantiation was not
     *         possible.
     */
    public static Validator<?> newInstance(Class<? extends Validator<?>> validatorType,
            Class<?> fieldType,
            String mask,
            double max,
            double min) {
        Validator<?> validator = newInstance(validatorType, fieldType);
        if (validator != null) {
            if (mask != null && !Validator.NO_MASK.equals(mask)) {
                validator.setMask(mask);
            }
            if (max != Validator.NO_VALUE) {
                validator.setMax(max);
            }
            if (min != Validator.NO_VALUE) {
                validator.setMin(min);
            }
        }
        return validator;
    }

    /**
     * Creates instances of the {@link Validator} for the given validatorType
     * or, if the validatorType is null, it tries to instantiate a
     * {@link Validator} that matches the given field type.
     * 
     * @param validatorType
     *            The type of the {@link Validator} that should be instanciated.
     *            If that type is null or instatiation fails, a
     *            {@link Validator} matching the field type is created. If
     *            {@link NoValidator#class} this parameter will be ignored.
     * @param fieldType
     *            The field type that needs to be validated.
     * @return A new {@link Validator} or null if instantiation was not
     *         possible.
     */
    static Validator<?> newInstance(Class<? extends Validator<?>> validatorType,
            Class<?> fieldType) {
        if (validatorType != null && !NoValidator.class.equals(validatorType)) {
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
        if (fieldType == null) {
            return null;
        }
        if (ClassUtils.isString(fieldType)) {
            return new StringValidator();
        }
        if (ClassUtils.isNumber(fieldType)) {
            return new NumberValidator();
        }
        if (ClassUtils.isBoolean(fieldType)) {
            return new BooleanValidator();
        }
        return null;
    }
}
