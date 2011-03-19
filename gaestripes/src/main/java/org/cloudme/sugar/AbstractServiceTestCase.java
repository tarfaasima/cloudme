package org.cloudme.sugar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.controller.UrlBinding;
import net.sourceforge.stripes.controller.UrlBindingFactory;
import net.sourceforge.stripes.controller.UrlBindingParameter;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationError;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class AbstractServiceTestCase {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
            new LocalMemcacheServiceTestConfig(),
            new LocalUserServiceTestConfig()).setEnvIsLoggedIn(true).setEnvEmail("test@mydomain.com")
            .setEnvAuthDomain("test");
    private Injector injector;

    @Before
    public void setUp() {
        helper.setUp();
        injector = Guice.createInjector(getModules());
        injector.injectMembers(this);
    }

    protected abstract Module[] getModules();

    protected void injectMembers(Object obj) {
        injector.injectMembers(obj);
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    protected <T extends ActionBean> T createActionBean(String url, Class<T> clazz) throws Exception {
        return createActionBean(url, clazz, null);
    }

    @SuppressWarnings( "unchecked" )
    protected <T extends ActionBean> T createActionBean(String url, Class<T> clazz, Map<String, Object> params)
            throws Exception {
        // MockServletContext context = new MockServletContext("test");
        // Map<String, String> params = new HashMap<String, String>();
        // params.put("ActionResolver.Packages",
        // "org.cloudme.loclist.stripes.action");
        // params.put("Extension.Packages",
        // "org.cloudme.loclist.stripes.extensions");
        // context.addFilter(StripesFilter.class, "StripesFilter", params);
        // context.setServlet(DispatcherServlet.class, "StripesDispatcher",
        // null);
        //
        // MockRoundtrip roundtrip = new MockRoundtrip(context, clazz);

        T instance = clazz.newInstance();
        injectMembers(instance);

        UrlBindingFactory factory = new UrlBindingFactory();// .getInstance();
        factory.addBinding(clazz, UrlBindingFactory.parseUrlBinding(clazz));
        UrlBinding binding = factory.getBinding(url);

        Method eventMethod = null;
        // String action = null;

        for (UrlBindingParameter parameter : binding.getParameters()) {
            String name = parameter.getName();
            // String value = parameter.getValue();
            if (name.equals("$event")) {
                if (parameter.getValue() != null) {
                    eventMethod = clazz.getDeclaredMethod(parameter.getValue());
                }
                // action = value;
            }
            else {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                Validate validate = field.getAnnotation(Validate.class);
                Object value = null;
                if (validate != null) {
                    Class<? extends TypeConverter> converter = validate.converter();
                    if (converter != null) {
                        value = converter.newInstance().convert(parameter.getValue(),
                                field.getType(),
                                new ArrayList<ValidationError>());
                    }
                }
                if (value == null && parameter.getValue() != null) {
                    if (Number.class.isAssignableFrom(field.getType())) {
                        value = DecimalFormat.getNumberInstance().parse(parameter.getValue());
                    }
                }
                field.set(instance, value);
                // roundtrip.setParameter(name, value);
            }
        }

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Field field = clazz.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(instance, entry.getValue());
            }
        }

        if (eventMethod == null) {
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(DefaultHandler.class)) {
                    eventMethod = m;
                }
            }
        }

        if (eventMethod != null) {
            eventMethod.invoke(instance);
        }
        // if (action == null) {
        // roundtrip.execute();
        // }
        // else {
        // roundtrip.execute(action);
        // }
        // return roundtrip.getActionBean(clazz);

        return instance;
    }
}
