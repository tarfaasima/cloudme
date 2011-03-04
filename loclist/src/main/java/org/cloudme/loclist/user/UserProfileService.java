package org.cloudme.loclist.user;


import com.google.appengine.api.users.User;
import com.google.inject.Inject;

public class UserProfileService {
    @Inject
    private UserProfileDao userProfileDao;

    public void login(User user) {
        if (!exists(user)) {
            UserProfile userProfile = new UserProfile();
            userProfile.setAuthDomain(user.getAuthDomain());
            userProfile.setEmail(user.getEmail());
            userProfile.setFederatedIdentity(user.getFederatedIdentity());
            userProfile.setNickname(user.getNickname());
            userProfile.setUserId(user.getUserId());
            userProfileDao.put(userProfile);
        }
    }

    private boolean exists(User user) {
        return userProfileDao.findSingleByUserId(user.getUserId()) != null;
    }
}
