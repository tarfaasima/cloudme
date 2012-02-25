<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default title="${actionBean.note.id != null ? 'Edit' : 'Create'} Note">
  <script>
    $(document).ready(function() {
      var inputTopic = $("#topic");
      inputTopic.autocomplete({
        source: [${f:join(actionBean.topics, "\"", "\"", ", ")}],
        autoFocus: true
      });
      
      if (inputTopic.val()) {
        $("#content").focus();
      }
      else {
        inputTopic.focus();
      }
    });
  </script>
  <s:form beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" method="post" id="noteEntry">
    <c:if test="${actionBean.note.id != null}">
      <s:hidden name="note.id" />
      <s:hidden name="note.creationDateMillis" />
    </c:if>
    <c:if test="${actionBean.note.meetingId != null}">
      <s:hidden name="note.meetingId" />
    </c:if>
    <s:errors />
    <div class="row">
      <label for="date">Date:</label>
      <div class="field">
        <s:text id="date" name="date" formatPattern="dd.MM.yyyy" class="selectOnFocus updateTitle" value="${actionBean.date}" />
      </div>
    </div>
    <div class="row">
      <label for="topic">Topic:</label>
      <div class="field">
        <s:text id="topic" name="topic" class="selectOnFocus updateTitle" />
      </div>
    </div>
    <div class="row">
      <div class="textarea">
        <s:textarea name="note.content" id="content" class="selectOnFocus" />
      </div>
    </div>
    <div class="row">
      <label for="responsible">Responsible:</label>
      <div class="field">
        <s:text id="responsible" name="note.responsible" class="selectOnFocus" />
      </div>
    </div>
    <div class="row">
      <label for="dueDate">Due date:</label>
      <div class="field">
        <s:text id="dueDate" name="dueDate" class="selectOnFocus" value="${actionBean.note.dueDate}"
          formatPattern="dd.MM.yyyy" />
      </div>
    </div>
    <div id="controls">
      <div class="left">
        <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" class="cancel" event="cancel">
          <c:if test="${actionBean.note.meetingId != null}">
            <s:param name="note.meetingId">${actionBean.note.meetingId}</s:param>
          </c:if>
          <c:if test="${actionBean.note.id != null}">
            <s:param name="note.id">${actionBean.note.id}</s:param>
          </c:if>
          Cancel
        </s:link>
        <c:if test="${actionBean.note.id != null}">
          <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" class="delete" event="delete"
            title="Do you really want to delete the note?">
            <s:param name="note.meetingId">${actionBean.note.meetingId}</s:param>
            <s:param name="note.id">${actionBean.note.id}</s:param>
            Delete
          </s:link>
        </c:if>
      </div>
      <div class="right">
        <s:submit value="${actionBean.note.id != null ? 'Update' : 'Save'}" name="save" />
      </div>
    </div>
  </s:form>
  <c:choose>
    <c:when test="${actionBean.note.meetingId != null}">
      <t:notes notes="${actionBean.notes}" currentNoteId="${actionBean.note.id}"/>
    </c:when>
    <c:otherwise>
      <c:if test="${fn:length(actionBean.recentMeetings) > 0}">
        <t:meetings groups="${actionBean.recentMeetings}" beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" event="create" name="note.meetingId"/>
      </c:if>
    </c:otherwise>
  </c:choose>
</t:default>