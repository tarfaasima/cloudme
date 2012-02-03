<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:default title="Meeting List new">
  <jsp:attribute name="content">
    <c:set var="lastDate" />
    <c:forEach items="${actionBean.meetings}" var="meeting">
      <c:if test="${meeting.date != lastDate}">
        <div>${meeting.date}</div>
        <c:set var="lastDate" value="${meeting.date}" />
      </c:if>
      <div class="meeting">
        <s:link beanclass="org.cloudme.notepad.stripes.action.meeting.MeetingActionBean" event="show">
          <s:param name="meeting.id">${meeting.id}</s:param>
            ${f:escapeHtml(meeting.topic)}
            (<fmt:formatDate value="${meeting.date}"/>)
          </s:link>
      </div>
    </c:forEach>
  </jsp:attribute>
</t:default>
