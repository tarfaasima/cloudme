package org.cloudme.stripestools;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ForwardResolution;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractActionBean implements ActionBean {
    private static final Log LOG = LogFactory.getLog(AbstractActionBean.class);
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
        String name = getClass().getName();
        String path = "/WEB-INF/classes/" + name.replace('.', '/') + ".jsp";
        if (LOG.isDebugEnabled()) {
            LOG.debug("Forward path: " + path);
        }
        return new ForwardResolution(path);
    }
}
