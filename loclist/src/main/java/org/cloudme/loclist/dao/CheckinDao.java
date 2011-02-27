package org.cloudme.loclist.dao;

import org.cloudme.loclist.model.Checkin;

@Deprecated
public class CheckinDao extends BaseDao<Checkin> {
    public CheckinDao() {
        super(Checkin.class);
    }
}
