<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default>
  <jsp:attribute name="title">
    ${actionBean.meeting.topic} - <t:date date="${actionBean.meeting.date}" />
  </jsp:attribute>
  <jsp:attribute name="h1">
    ${actionBean.meeting.topic} - <span class="date"><t:date date="${actionBean.meeting.date}" /></span>
  </jsp:attribute>
  <jsp:body>
    <t:notes notes="${actionBean.notes}" />
    <div id="controls">
      <div class="left">
        <s:link beanclass="org.cloudme.notepad.stripes.action.export.ExportActionBean">
          <s:param name="meeting.id">${actionBean.meeting.id}</s:param>
          Export
        </s:link>
      </div>
      <div class="right">
        <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" event="create">
          <s:param name="note.meetingId">${actionBean.meeting.id}</s:param>
          Create Note
        </s:link>
      </div>
    </div>
  </jsp:body>
</t:default>
