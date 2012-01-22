<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="n" uri="/WEB-INF/tags/notepad.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="apple-touch-icon" href="/apple-touch-icon-114x114.png" />
<meta name="viewport"
  content="width = device-width, initial-scale = 1, minimum-scale = 1, maximum-scale = 1, user-scalable = no" />
<title>Notepad</title>
<link href="/css/notepad.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.7.1.min.js"></script>
<script src="/js/jquery-ui-1.8.17.custom.min.js"></script>
<script>
  $(document).ready(function() {
	$(".selectOnFocus").focus(function() {
      this.setSelectionRange(0, $(this).val().length);
	});
	var inputTopic = $("#topic");
    inputTopic.autocomplete({
      source: [${n:join(actionBean.topics, "\"", "\"", ", ")}],
      autoFocus: true
    });
    if (inputTopic.val()) {
      $("#content").focus();
    }
    else {
      inputTopic.focus();
    }
    $("a").on("click", function(event) {
      event.preventDefault();
      if (!$(this).hasClass("delete") || confirm($(this).attr("title"))) {
        document.location.href = $(this).attr("href");
      }
    });
  });
</script>
</head>
<body>
  <div id="menu">
    <a href="#">Meetings</a> <a href="#">Note</a> <a href="#">To-Do</a>
  </div>
  <div id="header">
    <h1>${actionBean.note.managed ? "Edit" : "Create"} Note</h1>
  </div>
  <s:form beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" method="post" id="noteEntry">
    <s:hidden name="note.id" />
    <s:hidden name="note.meetingId" />
    <div class="row">
      <label for="date">Date:</label>
      <div class="field">
        <s:text id="date" name="date" formatPattern="medium" class="selectOnFocus" />
      </div>
    </div>
    <div class="row">
      <label for="topic">Topic:</label>
      <div class="field">
        <s:text id="topic" name="topic" class="selectOnFocus" />
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
          formatPattern="medium" />
      </div>
    </div>
    <div id="controls">
      <div class="left">
        <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" class="cancel">Cancel</s:link>
        <c:if test="${actionBean.note.managed}">
          <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" class="delete" event="delete"
            title="Do you really want to delete the note?">
            <s:param name="id">${actionBean.note.id}</s:param>
            Delete
          </s:link>
        </c:if>
      </div>
      <div class="right">
        <s:submit value="${actionBean.note.managed ? 'Update' : 'Save'}" name="save" />
      </div>
    </div>
  </s:form>
  <c:forEach items="${actionBean.notes}" var="note">
    <c:if test="${note.id != actionBean.id}">
      <div class="note">${note.content}</div>
    </c:if>
  </c:forEach>
</body>
</html>