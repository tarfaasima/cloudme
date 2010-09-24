package org.cloudme.stripestools;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ForwardResolution;

public class AbstractActionBean implements ActionBean {
    private ActionBeanContext context;

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    protected ForwardResolution jspForwardResolution() {
        return new ForwardResolution("/WEB-INF/classes/" + getClass().getName().replace('.', '/') + ".jsp");
    }
}
