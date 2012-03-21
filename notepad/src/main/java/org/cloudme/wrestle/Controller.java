package org.cloudme.wrestle;

import static org.cloudme.wrestle.annotation.AnnotationUtils.hasAnnotation;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;

import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.Post;
import org.cloudme.wrestle.annotation.UrlMapping;
import org.cloudme.wrestle.json.JsonWriter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Controller extends HttpServlet {
    private final Collection<RequestHandler> httpGetHandlers = new ArrayList<RequestHandler>();
    private final Collection<RequestHandler> httpPostHandlers = new ArrayList<RequestHandler>();
    private Injector injector;

    protected void register(Module... modules) {
        injector = Guice.createInjector(modules);
        injector.injectMembers(this);
    }

    protected void register(ActionHandler handler) {
        String urlMapping = handler.getClass().getAnnotation(UrlMapping.class).value();
        if (!urlMapping.endsWith("/")) {
            urlMapping += "/";
        }
        for (Method method : handler.getClass().getMethods()) {
            addIfHasAnnotation(Get.class, method, handler, urlMapping, httpGetHandlers);
            addIfHasAnnotation(Post.class, method, handler, urlMapping, httpPostHandlers);
        }
        injector.injectMembers(handler);
    }

    private void addIfHasAnnotation(Class<? extends Annotation> annotation,
            Method method,
            ActionHandler handler,
            String urlMapping,
            Collection<RequestHandler> requestHandlers) {
        if (hasAnnotation(method, annotation)) {
            String methodMapping = urlMapping + method.getName();
            requestHandlers.add(new RequestHandler(handler, method, methodMapping));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp, httpGetHandlers);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp, httpPostHandlers);
    }

    @SneakyThrows
    private void execute(HttpServletRequest req, HttpServletResponse resp, Collection<RequestHandler> requestHandlers) {
        String path = req.getPathInfo();
        for (RequestHandler handler : requestHandlers) {
            if (handler.matches(path)) {
                Object value = handler.execute(path, req, resp);
                resp.setContentType("application/json");
                JsonWriter json = new JsonWriter(resp.getWriter());
                json.write(value);
                return;
            }
        }
    }
}
