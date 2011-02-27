package org.cloudme.loclist.dao;

import org.cloudme.loclist.model.UserProfile;

public class UserProfileDao extends BaseDao<UserProfile> {
    public UserProfileDao() {
        super(UserProfile.class);
    }

    public boolean userIdExists(String userId) {
        return findBy(filter("userId", userId)).iterator().hasNext();
    }
}
