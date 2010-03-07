package org.cloudme.webgallery.stripes.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.SimpleMessage;

import org.cloudme.webgallery.message.Message;

public abstract class AbstractActionBean implements ActionBean {
    private ActionBeanContext ctx;

    public ActionBeanContext getContext() {
        return ctx;
    }

    public void setContext(ActionBeanContext context) {
        ctx = context;
    }

    protected String getJspPath(String urlBinding) {
        return "/WEB-INF/jsp" + urlBinding + ".jsp";
    }
    
    protected void addMessage(Message msg) {
        ctx.getMessages().add(new SimpleMessage(msg.getText(), msg.getParams()));
    }
}
