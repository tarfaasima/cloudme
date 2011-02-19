package org.cloudme.loclist.stripes.action;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;

import com.google.inject.Inject;

@UrlBinding( "/action/item/{$event}/{checkinId}/{noteItemId}" )
public class ItemActionBean extends AbstractActionBean {
    private static final Log LOG = LogFactory.getLog(ItemActionBean.class);
    private long checkinId;
    private long noteItemId;
    @Inject
    private ItemService itemService;

    public Resolution tick() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("tick(" + checkinId + ", " + noteItemId + ")");
        }
        itemService.tick(checkinId, noteItemId);
        return null;
    }

    //
    // public Resolution updateItemIndex() {
    // itemService.updateItemIndex();
    // return null;
    // }

    public long getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(long checkinId) {
        this.checkinId = checkinId;
    }

    public long getNoteItemId() {
        return noteItemId;
    }

    public void setNoteItemId(long noteItemId) {
        this.noteItemId = noteItemId;
    }
}
