package org.cloudme.loclist.stripes.action;

import static org.cloudme.gaestripes.BaseDao.filter;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.controller.UrlBinding;
import net.sourceforge.stripes.controller.UrlBindingFactory;
import net.sourceforge.stripes.controller.UrlBindingParameter;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationError;

import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.ItemInstanceDao;
import org.cloudme.loclist.dao.ItemListDao;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

public class CheckinActionBeanTest extends AbstractServiceTestCase {
    @Inject
    private ItemDao itemDao;
    @Inject
    private ItemInstanceDao itemInstanceDao;
    @Inject
    private ItemListDao itemListDao;

    @Before
    public void generateTestData() {
        createItems(itemDao, "Bread", "Apple", "Cheese");
        createItemList(itemDao, itemListDao, itemInstanceDao, "Test 1", "Apple", "Bread");
        createItemList(itemDao, itemListDao, itemInstanceDao, "Test 2", "Cheese");
    }

    private static void createItemList(ItemDao itemDao,
            ItemListDao itemListDao,
            ItemInstanceDao itemInstanceDao,
            String name,
            String... texts) {
        ItemList itemList = new ItemList();
        itemList.setName(name);
        itemListDao.save(itemList);

        for (String text : texts) {
            Item item = itemDao.listAll(filter("text", text)).get(0);
            ItemInstance itemInstance = new ItemInstance();
            itemInstance.setItemId(item.getId());
            itemInstance.setItemListId(itemList.getId());
            itemInstance.setText(text);
            itemInstanceDao.save(itemInstance);
        }
    }

    private static void createItems(ItemDao itemDao, String... texts) {
        for (String text : texts) {
            Item item = new Item();
            item.setText(text);
            itemDao.save(item);
        }
    }

    @Test
    public void testShow() throws Exception {
        String url = "/action/checkin/1/53.486748985000006/10.21923223";
        CheckinActionBean actionBean = createActionBean(url, CheckinActionBean.class);
        actionBean.show();
    }

    @SuppressWarnings( "unchecked" )
    private static <T extends ActionBean> T createActionBean(String url, Class<T> clazz) throws InstantiationException,
            IllegalAccessException, NoSuchFieldException {
        T instance = clazz.newInstance();

        UrlBindingFactory factory = UrlBindingFactory.getInstance();
        factory.addBinding(clazz, UrlBindingFactory.parseUrlBinding(clazz));
        UrlBinding binding = factory.getBinding(url);

        for (UrlBindingParameter parameter : binding.getParameters()) {
            Field field = clazz.getDeclaredField(parameter.getName());
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
            if (value == null) {
                if (field.getType().equals(Long.class)) {
                    value = Long.valueOf(parameter.getValue());
                }
            }
            field.set(instance, value);
        }
        return instance;
    }
}
