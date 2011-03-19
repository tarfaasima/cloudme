package org.cloudme.loclist.user;

import org.cloudme.sugar.AbstractDao;

class UserProfileDao extends AbstractDao<UserProfile> {
    public UserProfileDao() {
        super(UserProfile.class);
    }

    public UserProfile findSingleByUserId(String userId) {
        return findSingle("userId", userId);
    }
}
