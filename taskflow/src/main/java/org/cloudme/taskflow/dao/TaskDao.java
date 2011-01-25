package org.cloudme.taskflow.dao;

import org.cloudme.taskflow.domain.Task;

public class TaskDao extends BaseDao<Task> {
    public TaskDao() {
        super(Task.class);
    }
}
