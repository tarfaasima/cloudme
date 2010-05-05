package org.cloudme.triangle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.cloudme.triangle.annotations.AnnotationHelper;
import org.cloudme.triangle.annotations.Label;
import org.cloudme.triangle.annotations.Min;
import org.cloudme.triangle.annotations.Required;
import org.cloudme.triangle.validation.RequiredValidator;
import org.cloudme.triangle.validation.ValidationHelper;
import org.cloudme.triangle.validation.Validator;

public class Attribute {
    private final String name;
    private final String label;
    private final boolean isRequired;
    private final Collection<Validator> validators = new ArrayList<Validator>();
    private final Field field;

    public Attribute(Entity entity, Field field) {
        this.field = field;
        field.setAccessible(true);

        name = field.getName();
        label = new AnnotationHelper<String>(field, Label.class, name).value();
        isRequired = new AnnotationHelper<Boolean>(field, Required.class, false).value();
        if (isRequired) {
            validators.add(new RequiredValidator());
        }
        ValidationHelper.addTo(validators, field, Min.class);
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isRequired() {
        return isRequired;
    }

    void validate(Object object) {
        for (final Validator validator : validators) {
            try {
                validator.validate(field.get(object));
            } catch (final IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (final IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
