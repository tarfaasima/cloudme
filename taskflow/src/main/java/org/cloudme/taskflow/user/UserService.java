package org.cloudme.taskflow.user;

import org.cloudme.taskflow.dao.UserDao;
import org.cloudme.taskflow.domain.User;

import com.google.inject.Inject;

public class UserService {
    @Inject
    private UserDao userDao;

    public User create(String email, String nickname, String userId) {
        User user = userDao.findByUserId(userId);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(nickname);
            user.setUserId(userId);
            userDao.put(user);
        }
        return user;
    }
}
