package org.cloudme.taskflow.guice;

import org.cloudme.taskflow.dao.TaskDao;
import org.cloudme.taskflow.dao.UserDao;
import org.cloudme.taskflow.task.TaskService;
import org.cloudme.taskflow.user.UserService;

import com.google.inject.AbstractModule;

public class TaskFlowModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TaskDao.class);
        bind(TaskService.class);
        bind(UserDao.class);
        bind(UserService.class);
    }
}
