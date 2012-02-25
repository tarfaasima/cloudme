<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:default title="Topic List">
  <t:meetings groups="${actionBean.meetingGroups}" beanclass="org.cloudme.notepad.stripes.action.meeting.MeetingActionBean" event="show" name="meeting.id"/>
</t:default>
