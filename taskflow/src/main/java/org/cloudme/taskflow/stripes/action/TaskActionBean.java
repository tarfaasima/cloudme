package org.cloudme.taskflow.stripes.action;

import java.util.List;

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
    private List<Task> tasks;

    @DefaultHandler
    public Resolution show() {
        tasks = taskService.list();
        return resolve("task.jsp");
    }

    public Resolution create() {
        taskService.create(task);
        return redirect("/action/task");
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
