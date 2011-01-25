package org.cloudme.taskflow.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.taskflow.domain.Task;
import org.cloudme.taskflow.domain.User;

public abstract class BaseDao<T> extends AbstractDao<T> {
    static {
        register(Task.class);
        register(User.class);
    }

    public BaseDao(Class<T> baseClass) {
        super(baseClass);
    }
}
