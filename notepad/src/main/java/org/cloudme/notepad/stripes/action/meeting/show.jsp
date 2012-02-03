<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default>
  <jsp:attribute name="title">
    ${actionBean.meeting.topic} - <fmt:formatDate value="${actionBean.meeting.date}" pattern="dd.MM.yyyy"/>
  </jsp:attribute>
  <jsp:attribute name="h1">
    ${actionBean.meeting.topic} - <span class="date"><fmt:formatDate value="${actionBean.meeting.date}" pattern="dd.MM.yyyy"/></span>
  </jsp:attribute>
  <jsp:attribute name="content">
    <t:notes notes="${actionBean.notes}" />
    <div id="controls">
      <div class="right">
        <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" event="create">
          <s:param name="note.meetingId">${actionBean.meeting.id}</s:param>
          Create Note
        </s:link>
      </div>
    </div>
  </jsp:attribute>
</t:default>
