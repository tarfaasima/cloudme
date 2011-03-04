package org.cloudme.taskflow.domain;

import java.util.Date;

import javax.persistence.Transient;

import org.cloudme.gaestripes.Entity;

public class Task extends Entity {
    private String title;
    private String content;
    private Date dueDate;
    private String priority;
    @Transient
    private String tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
