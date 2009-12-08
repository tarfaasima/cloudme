package org.cloudme.stripes.tag;

import net.sourceforge.stripes.exception.StripesJspException;
import net.sourceforge.stripes.tag.DefaultPopulationStrategy;
import net.sourceforge.stripes.tag.InputTagSupport;

public class CustomPopulationStrategy extends DefaultPopulationStrategy {
    @Override
    public Object getValue(InputTagSupport tag) throws StripesJspException {
        Object value = getValueFromTag(tag);
        if (value == null) {
            value = super.getValue(tag);
        }
        return value;
    }
}
