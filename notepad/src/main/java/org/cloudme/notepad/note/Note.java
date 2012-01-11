package org.cloudme.notepad.note;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cloudme.sugar.Entity;

@Getter
@Setter
public class Note extends Entity {
    private String responsible;
    private String content;
    private Date dueDate;
    private Long meetingId;
}
