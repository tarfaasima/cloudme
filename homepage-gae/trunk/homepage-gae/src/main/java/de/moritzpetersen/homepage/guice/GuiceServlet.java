package de.moritzpetersen.homepage.guice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings( "serial" )
public class GuiceServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        Injector injector = Guice.createInjector(new HomepageModule());
        injector.injectMembers(this);
    }
}
