package de.moritzpetersen.homepage.stripes.action.admin;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dao.RssFeedDao;
import de.moritzpetersen.homepage.domain.RssFeed;
import de.moritzpetersen.homepage.stripes.action.AbstractActionBean;

@UrlBinding( "/admin/rssconfig/{$event}/{id}" )
public class RssConfigActionBean extends AbstractActionBean {
    @Inject
    private RssFeedDao rssFeedDao;
    @ValidateNestedProperties( { @Validate( field = "url", required = true ) } )
    private List<RssFeed> rssFeeds;
    private Long id;

    public void setRssFeeds(List<RssFeed> rssFeeds) {
        this.rssFeeds = rssFeeds;
    }

    public List<RssFeed> getRssFeeds() {
        return rssFeeds;
    }

    @DontValidate
    @DefaultHandler
    public Resolution show() {
        rssFeeds = new ArrayList<RssFeed>();
        for (RssFeed rssFeed : rssFeedDao.findAll()) {
            System.out.println(rssFeed.getId());
            rssFeeds.add(rssFeed);
        }
        System.out.println("size = " + rssFeeds.size());
        return new ForwardResolution("/WEB-INF/jsp/admin/rssconfig.jsp");
    }

    public Resolution save() {
        for (RssFeed rssFeed : rssFeeds) {
            rssFeedDao.save(rssFeed);
        }
        return new RedirectResolution(getClass());
    }

    @DontValidate
    public Resolution delete() {
        rssFeedDao.delete(id);
        return new RedirectResolution(getClass());
    }
}
