package org.cloudme.webgallery.stripes.action.organize;

import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/p/organize/photo")
public class PhotoActionBean extends AbstractActionBean {
    private Photo photo;
}
