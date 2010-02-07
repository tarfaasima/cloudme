package org.cloudme.webgallery.stripes.action.organize;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.stripes.action.AbstractActionBean;

@UrlBinding("/organize/settings/{$event}")
public class SettingsActionBean extends AbstractActionBean {
    @SpringBean
    private CacheService cacheService;
    
    public Resolution invalidateCache() {
        cacheService.invalidate();
        return new ForwardResolution(AlbumActionBean.class);
    }
}
