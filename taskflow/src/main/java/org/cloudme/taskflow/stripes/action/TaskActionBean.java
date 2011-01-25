package org.cloudme.taskflow.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.taskflow.domain.Task;
import org.cloudme.taskflow.task.TaskService;

import com.google.inject.Inject;

@UrlBinding( "/action/task" )
public class TaskActionBean extends AbstractActionBean {
    @Inject
    private TaskService taskService;
    private Task task;

    @DefaultHandler
    public Resolution show() {
        return resolve("task.jsp");
    }

    public Resolution create() {
        taskService.create(task);
        return show();
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
