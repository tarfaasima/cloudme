package org.cloudme.passwolk.servlet;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

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

    protected <T> T parseRequest(HttpServletRequest req, T obj, String prefix) {
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                String name = method.getName();
                Pattern pattern = Pattern.compile("get([A-Z].*)");
                Matcher matcher = pattern.matcher(name);
                if (matcher.matches()) {

                    // String parameter = req.getParameter(prefix + group);
                }
            }
        }
        return obj;
    }
}
