package org.cloudme.notepad.meeting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MeetingGroup {
	private final Collection<Meeting> meetings = new ArrayList<Meeting>();
	private Date date;

	public void add(Meeting meeting) {
		if (date == null) {
			date = meeting.getDate();
		}
		meetings.add(meeting);
	}

	public Date getDate() {
		return date;
	}

    public Collection<Meeting> getMeetings() {
        return meetings;
    }
}
