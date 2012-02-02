<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="notes" required="true" type="java.util.List" %>
<%@ attribute name="currentNoteId" required="false" type="java.lang.Long" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tags/functions.tld" %>

<c:forEach items="${notes}" var="note">
  <c:if test="${note.id != currentNoteId}">
    <div class="note">
      <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" event="edit">
        <s:param name="note.meetingId">${note.meetingId}</s:param>
        <s:param name="note.id">${note.id}</s:param>
            ${fns:escapeHtml(note.content)}
      </s:link>
    </div>
  </c:if>
</c:forEach>
