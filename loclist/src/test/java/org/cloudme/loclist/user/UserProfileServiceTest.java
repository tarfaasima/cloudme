package org.cloudme.loclist.user;

import static org.junit.Assert.assertEquals;

import org.cloudme.loclist.test.BaseTestCase;
import org.junit.Test;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;

public class UserProfileServiceTest extends BaseTestCase {
    @Inject
    private UserProfileService userProfileService;
    @Inject
    private UserProfileDao userProfileDao;

    @Test
    public void testLogin() {
        assertEquals(0, userProfileDao.listAll().size());
        User user = UserServiceFactory.getUserService().getCurrentUser();
        userProfileService.login(user);
        assertEquals(1, userProfileDao.listAll().size());
        userProfileService.login(user);
        assertEquals(1, userProfileDao.listAll().size());
    }

}
