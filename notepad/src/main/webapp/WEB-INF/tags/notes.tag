<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="notes" required="true" type="java.util.List" %>
<%@ attribute name="currentNoteId" required="false" type="java.lang.Long" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tags/functions.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<script>
$(document).ready(function() {
  $("a.check").on("click", function(event) {
    event.preventDefault();
    event.stopImmediatePropagation();
    $.get($(this).attr("href"));
    $(this).parent().toggleClass("done");
  });
});
</script>
<ul id="notes">
<c:forEach items="${notes}" var="note">
  <c:if test="${note.id != currentNoteId}">
    <li class="note${note.done ? ' done' : ''}">
      <c:if test="${note.responsible != null || note.dueDate != null}">
        <s:url beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" event="check" var="url">
          <s:param name="note.meetingId">${note.meetingId}</s:param>
          <s:param name="note.id">${note.id}</s:param>
        </s:url>
        <a href="${url}" class="check"></a>
      </c:if>
      <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" event="edit" class="content">
        <s:param name="note.meetingId">${note.meetingId}</s:param>
        <s:param name="note.id">${note.id}</s:param>
        ${fns:escapeHtml(note.content)}
      </s:link>
      <c:if test="${note.responsible != null || note.dueDate != null}">
        <c:if test="${note.responsible != null}">
          <div class="responsible">
            ${note.responsible}
          </div>
        </c:if>
        <c:if test="${note.dueDate != null}">
          <div class="dueDate${note.overdue ? ' overdue' : ''}">
            <t:date date="${note.dueDate}" />
          </div>
        </c:if>
      </c:if>
    </li>
  </c:if>
</c:forEach>
</ul>