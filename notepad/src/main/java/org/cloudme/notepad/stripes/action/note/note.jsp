<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="n" uri="/WEB-INF/tags/notepad.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
      </div>
      <div class="right">
        <s:submit value="Save" name="save" />
      </div>
    </div>
  </s:form>
</body>
</html>