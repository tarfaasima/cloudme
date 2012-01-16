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
	protected static final class RequestParameter {
		private final String name;
		private final Object value;

		public RequestParameter(String name, Object value) {
			this.name = name;
			this.value = value;
		}
	}

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
	 * Redirects to this {@link ActionBean} with the given event.
	 * 
	 * @param event
	 *            The event of the {@link ActionBean}.
	 * @return The {@link Resolution}.
	 */
	protected Resolution redirectSelf(String event, RequestParameter... parameters) {
		RedirectResolution res = new RedirectResolution(getClass(), event);
		for (RequestParameter parameter : parameters) {
			res.addParameter(parameter.name, parameter.value);
		}
		return res;
	}

	/**
	 * Convenience method to create request parameters, used in
	 * {@link #redirectSelf(String)}.
	 * 
	 * @param name
	 *            Name of the parameter.
	 * @param value
	 *            Value of the parameter.
	 * @return The {@link RequestParameter}
	 */
	protected RequestParameter param(String name, Object value) {
		return new RequestParameter(name, value);
	}
}
