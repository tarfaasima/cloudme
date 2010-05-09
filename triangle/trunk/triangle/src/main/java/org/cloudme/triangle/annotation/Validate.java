package org.cloudme.triangle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.cloudme.triangle.Attribute;
import org.cloudme.triangle.annotation.Validate.Factory.NoValidator;
import org.cloudme.triangle.validation.Validator;
import org.cloudme.triangle.validation.ValidatorFactory;
import org.cloudme.util.ObjectUtils;

/**
 * Defines validation options of an {@link Attribute}. Use
 * {@link Factory#newInstance(Field)} to create new validators based on the
 * annotation configuration.
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Validate {

    /**
     * Creates {@link Validator} instances with the given annotation
     * configuration.
     * 
     * @author Moritz Petersen
     */
    static class Factory {
        /**
         * Represents a {@link Validator} type that does not validate. Used to
         * tag {@link Validate#type()} if no {@link Validator} should be used.
         * 
         * @author Moritz Petersen
         */
        static abstract class NoValidator implements Validator<Object> {
        }

        /**
         * Creates a new instance. If the field doesn't have a {@link Validate}
         * annotation, or if the field is of a type that doesn't have a default
         * {@link Validator} or if the type is not provided, then
         * <code>null</code> is returned.
         * 
         * @param field
         *            The field's annotations are used to create a
         *            {@link Validator} instance.
         * @return The {@link Validator} instance or <code>null</code> if no
         *         instance could be determine.
         */
        public static Validator<?> newInstance(Field field) {
            Validate annotation = field.getAnnotation(Validate.class);
            if (annotation != null) {
                Class<? extends Validator<?>> type = ObjectUtils
                        .checkNullOr(NoValidator.class, annotation.type());
                return ValidatorFactory.newInstance(type,
                        field.getType(),
                        annotation.mask(),
                        annotation.max(),
                        annotation.min());
            }
            return null;
        }
    }

    /**
     * A regular expression pattern that must be matched by the validated value.
     * 
     * @return A regular expression pattern that must be matched by the
     *         validated value.
     */
    String mask() default Validator.NO_MASK;

    /**
     * The maximum that is allowed for validation.
     * 
     * @return The maximum that is allowed for validation.
     */
    double max() default Validator.NO_VALUE;

    /**
     * The minimum that is allowed for validation.
     * 
     * @return The minimum that is allowed for validation.
     */
    double min() default Validator.NO_VALUE;

    /**
     * The type of validator that should be used instead of the default
     * validator.
     * 
     * @return The type of validator that should be used instead of the default
     *         validator.
     */
    Class<? extends Validator<?>> type() default NoValidator.class;
}
