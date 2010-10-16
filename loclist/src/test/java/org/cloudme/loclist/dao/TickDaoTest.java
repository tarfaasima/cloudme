package org.cloudme.loclist.dao;

import static org.junit.Assert.assertEquals;

import org.cloudme.loclist.model.Tick;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Test;

import com.google.inject.Inject;

public class TickDaoTest extends AbstractServiceTestCase {
    @Inject
    private TickDao tickDao;

    @Test
    public void testDeleteByCheckinAndItem() {
        Tick tick = new Tick();
        tick.setCheckinId(1L);
        tick.setItemId(2L);
        tick.setTimestamp(System.currentTimeMillis());
        tickDao.save(tick);

        assertEquals(1, tickDao.listAll().size());

        Tick tick2 = tickDao.findAll().iterator().next();
        assertEquals(new Long(1), tick2.getCheckinId());
        assertEquals(new Long(2), tick2.getItemId());

        tickDao.deleteByCheckinAndItem(1L, 2L);

        assertEquals(0, tickDao.listAll().size());

        tickDao.deleteByCheckinAndItem(1L, 2L);
    }
}
