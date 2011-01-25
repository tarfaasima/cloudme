package org.cloudme.taskflow.dao;

import org.cloudme.taskflow.domain.User;

public class UserDao extends BaseDao<User> {
    public UserDao() {
        super(User.class);
    }

    public User findByUserId(String userId) {
        return findSingle("userId", userId);
    }
}
