package org.cloudme.notepad.meeting;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cloudme.sugar.Entity;

@Getter
@Setter
public class Meeting extends Entity {
    private Date date;
    private String topic;
}
