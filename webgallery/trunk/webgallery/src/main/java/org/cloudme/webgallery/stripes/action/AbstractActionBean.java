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
        String userAgent = ctx.getRequest().getHeader("User-Agent");
        String prefix = "/default";
        if (isIphone(userAgent)) {
            prefix = "/iphone";
        }
        return "/WEB-INF/jsp" + prefix + urlBinding + ".jsp";
    }
    
    private boolean isIphone(String userAgent) {
        return userAgent.contains("iPhone") && userAgent.contains("AppleWebKit");
    }

    protected void addMessage(Message msg) {
        if (msg == null) {
            return;
        }
        ctx.getMessages().add(new SimpleMessage(msg.getText(), msg.getParams()));
    }
}
