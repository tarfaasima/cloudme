package org.cloudme.passwolk.servlet;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.cloudme.passwolk.account.AccountModule;

import com.google.inject.Guice;
import com.google.inject.Module;

public abstract class AbstractGuiceServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        Guice.createInjector(getModules()).injectMembers(this);
    }

    private Module[] getModules() {
        return new Module[] { new AccountModule() };
    }

    @SuppressWarnings( "unchecked" )
    protected <T> T parseRequest(HttpServletRequest req, T obj, String prefix) {
        Map<String, Object> propertiesMap = null;
        try {
            propertiesMap = BeanUtils.describe(obj);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Unable to get properties of object " + obj, e);
        }
        try {
            for (String propertyName : propertiesMap.keySet()) {
                String parameter = req.getParameter(prefix + "." + propertyName);
                if (parameter != null) {
                    BeanUtils.setProperty(obj, propertyName, parameter);
                }
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Unable to populate properties of object " + obj, e);
        }
        return obj;
    }
}
