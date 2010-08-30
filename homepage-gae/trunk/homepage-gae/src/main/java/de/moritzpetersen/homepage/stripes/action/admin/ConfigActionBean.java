package de.moritzpetersen.homepage.stripes.action.admin;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.domain.Config;
import de.moritzpetersen.homepage.service.ConfigService;
import de.moritzpetersen.homepage.stripes.action.AbstractActionBean;

@UrlBinding( "/admin/config.php" )
public class ConfigActionBean extends AbstractActionBean {
    @Inject
    private ConfigService configService;
    private Config config;

    @DefaultHandler
    public Resolution show() {
        config = configService.get();
        return new ForwardResolution("/WEB-INF/jsp/admin/config.jsp");
    }
}
