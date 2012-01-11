<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Notepad</title>
<link href="/css/ui-lightness/jquery-ui-1.8.17.custom.css" rel="stylesheet" type="text/css"/>
<!-- <link href="/css/notepad.css" rel="stylesheet" type="text/css"/> -->
<script src="/js/jquery-1.7.1.min.js"></script>
<script src="/js/jquery-ui-1.8.17.custom.min.js"></script>
<script>
  $(document).ready(function() {
    $("input#topic").autocomplete({
      source: [${actionBean.topicsAsString}],
      autoFocus: true
    });
  });
</script>
</head>
<body class="ui-widget">
<h1>Edit Note</h1>
<s:form beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" method="post">
<div>Date</div>
<div>
<s:text name="date"/>
</div>
<div>Topic</div>
<div>
<s:text name="topic" id="topic"/>
</div>
<div>Responsible</div>
<div>
<s:text name="note.responsible"/>
</div>
<div>Content</div>
<div>
<s:text name="note.content"/>
</div>
<div>Due Date</div>
<div>
<s:text name="note.dueDate"/>
</div>
<s:submit value="Save" name="save"/>
</s:form>
</body>
</html>