package org.cloudme.webgallery.stripes.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.ActionBeanContext;
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
    
    @SuppressWarnings("unchecked")
    @DefaultHandler
    public Resolution update() {
        ActionBeanContext context = getContext();
        HttpServletRequest request = context.getRequest();
        for (Enumeration<String> names = request.getHeaderNames(); names.hasMoreElements();) {
            String name = names.nextElement();
            String header = request.getHeader(name);
            System.out.println(name + " : " + header);
        }
        cacheUpdateService.update(timeout);
        return new ForwardResolution("/gallery/home");
    }
}
