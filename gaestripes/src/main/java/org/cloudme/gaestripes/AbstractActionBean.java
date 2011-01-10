package org.cloudme.gaestripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

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

    protected Resolution resolve(String file) {
        return resolve(file, false);
    }

    protected Resolution resolve(String file, boolean redirect) {
        String name = getClass().getPackage().getName();
        String path = "/WEB-INF/classes/" + name.replace('.', '/') + "/" + file;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Forward path: " + path);
        }
        return redirect ? new RedirectResolution(path) : new ForwardResolution(path);
    }

    protected Resolution redirect(String url) {
        return new RedirectResolution(url);
    }
}
