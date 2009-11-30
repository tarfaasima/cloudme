package org.cloudme.webgallery.stripes.util;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

public abstract class AbstractActionBean implements ActionBean {
    private ActionBeanContext context;

    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }
    
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(getPath(getClass().getAnnotation(UrlBinding.class).value()));
    }

    protected String getPath(String urlBinding) {
        return "/WEB-INF/jsp" + urlBinding + ".jsp";
    }
}
