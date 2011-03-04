package org.cloudme.taskflow.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.taskflow.domain.Task;

public class TaskDao extends AbstractDao<Task> {
    public TaskDao() {
        super(Task.class);
    }
}
