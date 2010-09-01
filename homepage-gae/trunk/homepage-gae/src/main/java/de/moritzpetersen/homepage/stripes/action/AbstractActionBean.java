package de.moritzpetersen.homepage.stripes.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.validation.SimpleError;

public abstract class AbstractActionBean implements ActionBean {
    private ActionBeanContext context;

    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    protected void addError(String msg, Object... params) {
        context.getMessages().add(new SimpleError(msg, params));
    }
}
