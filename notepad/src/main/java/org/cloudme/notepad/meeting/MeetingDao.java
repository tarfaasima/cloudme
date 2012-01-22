package org.cloudme.notepad.meeting;

import java.util.Date;

import org.cloudme.sugar.AbstractDao;

class MeetingDao extends AbstractDao<Meeting> {

    public MeetingDao() {
        super(Meeting.class);
    }

    public Meeting findSingleByDateAndTopic(Date date, String topic) {
        return findSingle(filter("date", date), filter("topic", topic));
    }

    public Iterable<Meeting> findAllOrderByTopic() {
        return findBy(orderBy("topic"));
    }

}
