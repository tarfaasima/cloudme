<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="n" uri="/WEB-INF/tags/notepad.tld"%>

<s:layout-render name="/WEB-INF/layout/default.jsp" title="Meeting List">
  <s:layout-component name="content">
    <c:forEach items="${actionBean.meetings}" var="meeting">
      <div class="meeting">
        <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" event="create">
          <s:param name="note.meetingId">${meeting.id}</s:param>
            ${n:escapeHtml(meeting.topic)}
            (<f:formatDate value="${meeting.date}"/>)
          </s:link>
      </div>
    </c:forEach>
  </s:layout-component>
</s:layout-render>
