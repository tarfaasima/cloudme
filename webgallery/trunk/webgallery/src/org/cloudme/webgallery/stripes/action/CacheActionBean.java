package org.cloudme.webgallery.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.service.CacheUpdateService;

@UrlBinding("/gallery/cache/{$event}")
public class CacheActionBean extends AbstractActionBean {
    @SpringBean
    private CacheUpdateService cacheUpdateService;
    
    @DefaultHandler
    public Resolution update() {
        cacheUpdateService.update();
        return new ForwardResolution("/gallery/home");
    }
}
