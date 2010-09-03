package de.moritzpetersen.homepage.stripes.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;

public abstract class AbstractActionBean implements ActionBean {
    private ActionBeanContext context;

    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    protected Resolution addValidationError(String messageKey, Object... params) {
        context.getValidationErrors().add("data", new LocalizableError(messageKey, params));
        return context.getSourcePageResolution();
    }
}
