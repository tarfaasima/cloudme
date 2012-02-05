<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:default title="Meeting List">
  <jsp:attribute name="content">
    <c:forEach items="${actionBean.meetingGroups}" var="group">
      <div class="date"><fmt:formatDate value="${group.date}" pattern="dd.MM.yyyy"/></div>
      <div class="meetings">
        <c:forEach items="${group.meetings}" var="meeting">
          <div class="meeting">
            <s:link beanclass="org.cloudme.notepad.stripes.action.meeting.MeetingActionBean" event="show">
              <s:param name="meeting.id">${meeting.id}</s:param>
                ${f:escapeHtml(meeting.topic)}
              </s:link>
          </div>
        </c:forEach>
      </div>
    </c:forEach>
  </jsp:attribute>
</t:default>
