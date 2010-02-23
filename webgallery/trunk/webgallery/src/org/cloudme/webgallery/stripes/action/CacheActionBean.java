package org.cloudme.webgallery.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.service.CacheUpdateService;

@UrlBinding("/gallery/cache/{$event}/{timeout}")
public class CacheActionBean extends AbstractActionBean {
    @SpringBean
    private CacheUpdateService cacheUpdateService;
    private final long timeout = 20000;
    
    @DefaultHandler
    public Resolution update() {
        cacheUpdateService.update(timeout);
        return new ForwardResolution("/gallery/home");
    }
}
