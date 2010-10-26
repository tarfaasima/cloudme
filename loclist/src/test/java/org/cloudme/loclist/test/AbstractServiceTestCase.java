package org.cloudme.loclist.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.controller.UrlBinding;
import net.sourceforge.stripes.controller.UrlBindingFactory;
import net.sourceforge.stripes.controller.UrlBindingParameter;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationError;

import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.ItemInstanceDao;
import org.cloudme.loclist.dao.ItemListDao;
import org.cloudme.loclist.guice.LoclistModule;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class AbstractServiceTestCase {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
            new LocalMemcacheServiceTestConfig());
    private Injector injector;
    @Inject
    protected ItemDao itemDao;
    @Inject
    private ItemInstanceDao itemInstanceDao;
    @Inject
    protected ItemListDao itemListDao;
    private final Map<String, Item> items = new HashMap<String, Item>();
    private final Map<String, ItemInstance> itemInstances = new HashMap<String, ItemInstance>();

    @Before
    public void setUp() {
        helper.setUp();
        injector = Guice.createInjector(new LoclistModule());
        injector.injectMembers(this);
    }

    protected void injectMembers(Object obj) {
        injector.injectMembers(obj);
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void dummyTest() {
    }

    protected void createItemList(String name, String... texts) {
        ItemList itemList = new ItemList();
        itemList.setName(name);
        itemListDao.save(itemList);

        for (String text : texts) {
            Item item = items.get(text);
            ItemInstance itemInstance = new ItemInstance();
            itemInstance.setItemId(item.getId());
            itemInstance.setItemListId(itemList.getId());
            itemInstance.setText(text);
            itemInstanceDao.save(itemInstance);
            itemInstances.put(text, itemInstance);
        }
    }

    protected ItemList itemList(String name) {
        return itemListDao.findSingle("name", name);
    }

    protected ItemInstance itemInstance(String text) {
        return itemInstances.get(text);
    }

    protected void createItems(String... texts) {
        for (String text : texts) {
            Item item = new Item();
            item.setText(text);
            itemDao.save(item);
            items.put(text, item);
        }
    }

    protected Item item(String text) {
        return items.get(text);
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

        UrlBindingFactory factory = UrlBindingFactory.getInstance();
        factory.addBinding(clazz, UrlBindingFactory.parseUrlBinding(clazz));
        UrlBinding binding = factory.getBinding(url);

        Method method = null;
        // String action = null;

        for (UrlBindingParameter parameter : binding.getParameters()) {
            String name = parameter.getName();
            // String value = parameter.getValue();
            if (name.equals("$event")) {
                if (parameter.getValue() != null) {
                    method = clazz.getDeclaredMethod(parameter.getValue());
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

        if (method == null) {
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(DefaultHandler.class)) {
                    method = m;
                }
            }
        }
        if (method != null) {
            method.invoke(instance);
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
