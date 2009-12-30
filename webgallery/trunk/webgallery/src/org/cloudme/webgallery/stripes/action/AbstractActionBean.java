package org.cloudme.webgallery.stripes.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

public abstract class AbstractActionBean implements ActionBean {
    private ActionBeanContext context;

    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    protected String getJspPath(String urlBinding) {
        return "/WEB-INF/jsp" + urlBinding + ".jsp";
    }
}
