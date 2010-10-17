package org.cloudme.loclist.stripes.action.list;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.gaestripes.AbstractActionBean;

@UrlBinding( "/action/list/{$event}" )
public class Index extends AbstractActionBean {
    private static final Log LOG = LogFactory.getLog(Index.class);

    @DefaultHandler
    public Resolution index() {
        return jspForwardResolution();
    }

    public Resolution create() {
        return jspForwardResolution();
    }
}
