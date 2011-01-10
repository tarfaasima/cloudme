package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.loclist.model.Checkin;

public class CheckinDao extends AbstractDao<Checkin> {
    public CheckinDao() {
        super(Checkin.class);
    }
}
