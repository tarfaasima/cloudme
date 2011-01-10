package org.cloudme.loclist.stripes.extensions;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.gaestripes.AbstractActionBeanResolutionInterceptor;
import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.ItemInstanceDao;
import org.cloudme.loclist.dao.ItemListDao;
import org.cloudme.loclist.dao.ItemOrderDao;
import org.cloudme.loclist.dao.LocationDao;
import org.cloudme.loclist.dao.TickDao;
import org.cloudme.loclist.dao.UpdateDao;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.loclist.log.UserLogService;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
import org.cloudme.loclist.model.ItemOrder;
import org.cloudme.loclist.model.Location;
import org.cloudme.loclist.model.Tick;
import org.cloudme.loclist.model.Update;
import org.cloudme.loclist.model.UserLog;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;

@Intercepts( { LifecycleStage.ActionBeanResolution } )
public class LoclistInterceptor extends AbstractActionBeanResolutionInterceptor {
    @Inject
    private UserLogService userLogService;

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        User user = UserServiceFactory.getUserService().getCurrentUser();
        if (user != null) {
            userLogService.log(user);
            NamespaceManager.set(user.getUserId());
            context.getActionBeanContext().getServletContext().setAttribute("user", user);
        }
        return super.intercept(context);
    }

    @Override
    protected Class<?>[] guiceModuleClasses() {
        return new Class[] { ItemDao.class, ItemInstanceDao.class, ItemListDao.class, ItemOrderDao.class,
                LocationDao.class, TickDao.class, UpdateDao.class, ItemService.class, LocationService.class };
    }

    @Override
    protected Class<?>[] objectifyEntityClasses() {
        return new Class<?>[] { Checkin.class, Item.class, ItemInstance.class, ItemList.class, ItemOrder.class,
                Location.class, Tick.class, Update.class, UserLog.class };
    }
}
