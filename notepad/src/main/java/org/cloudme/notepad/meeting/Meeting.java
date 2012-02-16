package org.cloudme.notepad.meeting;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cloudme.sugar.Entity;

import com.googlecode.objectify.annotation.Cached;

@Getter
@Setter
@Cached
public class Meeting extends Entity {
    private Date date;
    private String topic;
}
