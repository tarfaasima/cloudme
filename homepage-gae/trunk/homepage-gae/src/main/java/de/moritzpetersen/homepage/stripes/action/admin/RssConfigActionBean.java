package de.moritzpetersen.homepage.stripes.action.admin;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dao.RssFeedDao;
import de.moritzpetersen.homepage.domain.RssFeed;
import de.moritzpetersen.homepage.stripes.action.AbstractActionBean;

@UrlBinding( "/admin/rssconfig.php" )
public class RssConfigActionBean extends AbstractActionBean {
    @Inject
    private RssFeedDao rssFeedDao;
    private List<RssFeed> rssFeeds;

    public void setRssFeeds(List<RssFeed> rssFeeds) {
        this.rssFeeds = rssFeeds;
    }

    public List<RssFeed> getRssFeeds() {
        return rssFeeds;
    }

    @DefaultHandler
    public Resolution show() {
        rssFeeds = new ArrayList<RssFeed>();
        for (RssFeed rssFeed : rssFeedDao.findAll()) {
            rssFeeds.add(rssFeed);
        }
        return new ForwardResolution("/WEB-INF/jsp/admin/rssconfig.jsp");
    }

    public Resolution save() {
        for (RssFeed rssFeed : rssFeeds) {
            rssFeedDao.save(rssFeed);
        }
        return show();
    }
}
