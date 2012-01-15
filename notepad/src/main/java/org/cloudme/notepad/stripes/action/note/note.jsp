<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="n" uri="/WEB-INF/tags/notepad.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width = device-width, initial-scale = 1, minimum-scale = 1, maximum-scale = 1, user-scaleable = no" />
<title>Notepad</title>
<!-- <link href="/css/ui-lightness/jquery-ui-1.8.17.custom.css" -->
<!--   rel="stylesheet" type="text/css" /> -->
<link href="/css/notepad.css" rel="stylesheet" type="text/css"/>
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
<body class="ui-widget">
  <h1>Edit Note</h1>
  <s:form
    beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean"
    method="post"
    id="noteEntry">
    <div class="row">
      <label>Date:</label>
      <div class="field">
        <s:text name="date" formatPattern="medium" class="selectOnFocus" />
      </div>
    </div>
    <div class="row">
      <label>Topic:</label>
      <div class="field">
        <s:text name="topic" id="topic" class="selectOnFocus" />
      </div>
    </div>
    <div class="row">
      <div class="textarea">
        <s:textarea name="note.content" id="content" class="selectOnFocus" />
      </div>
    </div>
    <div class="row">
      <label>Responsible:</label>
      <div class="field">
        <s:text name="note.responsible" class="selectOnFocus" />
      </div>
    </div>
    <div class="row">
      <label>Due date:</label>
      <div class="field">
        <s:text name="dueDate" class="selectOnFocus" />
      </div>
    </div>
    <div class="submit">
      <s:submit value="Save" name="save" />
    </div>
  </s:form>
</body>
</html>