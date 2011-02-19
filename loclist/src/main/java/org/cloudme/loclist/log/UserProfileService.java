package org.cloudme.loclist.log;

import org.cloudme.loclist.dao.UserProfileDao;
import org.cloudme.loclist.model.UserProfile;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

public class UserProfileService {
    @Inject
    private UserProfileDao userProfileDao;

    public void log(User user) {
        if (!userProfileDao.userIdExists(user.getUserId())) {
            UserProfile userProfile = new UserProfile();
            userProfile.setAuthDomain(user.getAuthDomain());
            userProfile.setEmail(user.getEmail());
            userProfile.setFederatedIdentity(user.getFederatedIdentity());
            userProfile.setNickname(user.getNickname());
            userProfile.setUserId(user.getUserId());
            userProfileDao.save(userProfile);
        }
    }
}
