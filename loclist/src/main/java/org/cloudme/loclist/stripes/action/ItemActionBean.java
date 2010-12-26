package org.cloudme.loclist.stripes.action;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;

import com.google.inject.Inject;

@UrlBinding( "/action/item/{$event}/{checkinId}/{itemInstanceId}" )
public class ItemActionBean extends AbstractActionBean {
    private static final Log LOG = LogFactory.getLog(ItemActionBean.class);
    private long checkinId;
    private long itemInstanceId;
    @Inject
    private ItemService itemService;

    public Resolution tick() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("tick(" + checkinId + ", " + itemInstanceId + ")");
        }
        itemService.tick(checkinId, itemInstanceId);
        return null;
    }

    public long getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(long checkinId) {
        this.checkinId = checkinId;
    }

    public long getItemInstanceId() {
        return itemInstanceId;
    }

    public void setItemInstanceId(long itemInstanceId) {
        this.itemInstanceId = itemInstanceId;
    }
}
