package org.cloudme.sugar;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Default implementation of the {@link ActionBean} interface.
 * 
 * @author Moritz Petersen
 */
public abstract class AbstractActionBean implements ActionBean {
    private static final Log LOG = LogFactory.getLog(AbstractActionBean.class);
    private ActionBeanContext context;
    private final String path = "/WEB-INF/classes/" + getClass().getPackage().getName().replace('.', '/') + "/";

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    /**
     * Create a {@link ForwardResolution} to the file with the given name in the
     * same package as this class. This method allows to place the JSP files
     * directly into the same directory as the {@link ActionBean}.
     * 
     * @param file
     *            The name of the file (without path).
     * @return A {@link ForwardResolution} of the file.
     */
    protected Resolution resolve(String file) {
        return resolve(file, false);
    }

    /**
     * Create a {@link Resolution} to the file with the given name in the same
     * package as this class. This method allows to place the JSP files directly
     * into the same directory as the {@link ActionBean}.
     * 
     * @param file
     *            The name of the file (without path).
     * @param redirect
     *            If <tt>true</tt> creates a {@link RedirectResolution}
     *            otherwise a {@link ForwardResolution}.
     * @return A {@link ForwardResolution} or {@link RedirectResolution} of the
     *         file.
     */
    protected Resolution resolve(String file, boolean redirect) {
        String resolutionPath = path + file;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Resolution path: " + resolutionPath);
        }
        return redirect ? new RedirectResolution(resolutionPath) : new ForwardResolution(resolutionPath);
    }

    /**
     * Create a {@link RedirectResolution} to the given URL.
     * 
     * @param url
     *            The URL to which the Browser should be redirected.
     * @return The {@link RedirectResolution} to the URL
     */
    protected Resolution redirect(String url) {
        return new RedirectResolution(url);
    }

    /**
     * Create a {@link RedirectResolution} to the given {@link ActionBean}.
     * 
     * @param beanType
     *            The class of the {@link ActionBean}.
     * @return The {@link RedirectResolution}.
     */
    protected RedirectResolution redirect(Class<? extends ActionBean> beanType) {
        return new RedirectResolution(beanType);
    }

    /**
     * Create a {@link RedirectResolution} to the given {@link ActionBean}.
     * 
     * @param beanType
     *            The class of the {@link ActionBean}.
     * @param event
     *            The event of the {@link ActionBean}.
     * @return The {@link RedirectResolution}.
     */
    protected RedirectResolution redirect(Class<? extends ActionBean> beanType, String event) {
        return new RedirectResolution(beanType, event);
    }
}
