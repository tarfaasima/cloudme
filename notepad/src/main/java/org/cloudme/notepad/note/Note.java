package org.cloudme.notepad.note;

import java.util.Date;

import javax.persistence.PostLoad;

import lombok.Getter;
import lombok.Setter;

import org.cloudme.sugar.Entity;

import com.googlecode.objectify.annotation.Cached;

@Getter
@Setter
@Cached
public class Note extends Entity {
    private String responsible;
    private String content;
    private Date dueDate;
    private Long meetingId;
    private Date creationDate = new Date();
    private boolean done;
    private boolean todo;

	public long getCreationDateMillis() {
		return creationDate.getTime();
	}

	public void setCreationDateMillis(long millis) {
		creationDate = new Date(millis);
	}

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        updateIsTodo();
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
        updateIsTodo();
    }

    @PostLoad
    private void updateIsTodo() {
        todo = dueDate != null || responsible != null;
    }

    public boolean isOverdue() {
        return dueDate != null && dueDate.before(new Date());
    }
}
