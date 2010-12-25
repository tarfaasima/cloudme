package org.cloudme.loclist.log;

import org.cloudme.loclist.dao.UserLogDao;
import org.cloudme.loclist.model.UserLog;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

public class UserLogService {
    @Inject
    private UserLogDao userLogDao;

    public void log(User user) {
        if (!userLogDao.userIdExists(user.getUserId())) {
            UserLog userLog = new UserLog();
            userLog.setAuthDomain(user.getAuthDomain());
            userLog.setEmail(user.getEmail());
            userLog.setFederatedIdentity(user.getFederatedIdentity());
            userLog.setNickname(user.getNickname());
            userLog.setUserId(user.getUserId());
            userLogDao.save(userLog);
        }
    }
}
