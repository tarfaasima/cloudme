package org.cloudme.taskflow.task;

import org.cloudme.taskflow.dao.TaskDao;
import org.cloudme.taskflow.domain.Task;

import com.google.inject.Inject;

public class TaskService {
    @Inject
    private TaskDao taskDao;

    public void create(Task task) {
        taskDao.save(task);
    }
}
