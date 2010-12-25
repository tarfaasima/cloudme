package org.cloudme.loclist.dao;

import org.cloudme.loclist.model.UserLog;

public class UserLogDao extends AbstractDao<UserLog> {
    public UserLogDao() {
        super(UserLog.class);
    }

    public boolean userIdExists(String userId) {
        return findAll(filter("userId", userId)).iterator().hasNext();
    }
}
