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
    public static Validator newInstanceFor(Class<? extends Validator> validatorType, Class<?> type) {
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
