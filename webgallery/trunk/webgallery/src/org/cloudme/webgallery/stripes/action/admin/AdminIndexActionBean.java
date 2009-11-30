package org.cloudme.webgallery.stripes.action.admin;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/gallery/admin")
public class AdminIndexActionBean extends AbstractActionBean {
    @Override
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(getPath("/gallery/admin/index"));
    }
}
