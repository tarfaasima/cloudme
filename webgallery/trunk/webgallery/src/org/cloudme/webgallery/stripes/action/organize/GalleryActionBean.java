package org.cloudme.webgallery.stripes.action.organize;

import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.webgallery.Gallery;

@UrlBinding("/organize/gallery/{$event}/{id}")
public class GalleryActionBean extends AbstractOrganizeActionBean<Gallery> {
}
