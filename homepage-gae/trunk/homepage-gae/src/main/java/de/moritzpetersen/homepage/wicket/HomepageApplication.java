package de.moritzpetersen.homepage.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.ISessionStore;

import de.moritzpetersen.homepage.guice.HomepageModule;
import de.moritzpetersen.homepage.wicket.pages.Index;
import de.moritzpetersen.homepage.wicket.pages.admin.DataLoadPage;

public class HomepageApplication extends WebApplication {
    @Override
    public Class<? extends Page> getHomePage() {
        return Index.class;
    }

    @Override
    protected void init() {
        super.init();
        getResourceSettings().setResourcePollFrequency(null);
        addComponentInstantiationListener(new GuiceComponentInjector(this, new HomepageModule()));

        mountBookmarkablePage("/admin/dataload", DataLoadPage.class);
    }

    @Override
    protected ISessionStore newSessionStore() {
        return new HttpSessionStore(this);
    }
}
