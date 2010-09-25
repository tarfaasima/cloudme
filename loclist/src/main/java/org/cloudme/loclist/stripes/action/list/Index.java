package org.cloudme.loclist.stripes.action.list;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.stripestools.AbstractActionBean;

@UrlBinding( "/action/list/index" )
public class Index extends AbstractActionBean {
    private static final Log LOG = LogFactory.getLog(Index.class);

    @DefaultHandler
    public Resolution show() {
        LOG.info("show()");
        return jspForwardResolution();
    }
}
