package org.cloudme.gaestripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ForwardResolution;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractActionBean implements ActionBean {
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

    protected ForwardResolution resolve(String file) {
        String name = getClass().getPackage().getName();
        String path = "/WEB-INF/classes/" + name.replace('.', '/') + "/" + file + ".jsp";
        if (LOG.isDebugEnabled()) {
            LOG.debug("Forward path: " + path);
        }
        return new ForwardResolution(path);
    }
}
