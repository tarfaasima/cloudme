<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="h1" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="apple-touch-icon" href="apple-touch-icon-114x114-precomposed.png" />
<meta name="viewport"
  content="width = device-width, initial-scale = 1, minimum-scale = 1, maximum-scale = 1, user-scalable = no" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>Notepad - ${title}</title>
<link href="/css/notepad.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.7.1.min.js"></script>
<script src="/js/jquery-ui-1.8.17.custom.min.js"></script>
</head>
<body>
  <div id="menu">
    <s:link beanclass="org.cloudme.notepad.stripes.action.meeting.MeetingActionBean">Topics</s:link>
    <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean">Note</s:link>
    <s:link beanclass="org.cloudme.notepad.stripes.action.todo.TodoActionBean">Tasks</s:link>
  </div>
  <div id="header">
    <h1>${h1 == null ? title : h1}</h1>
  </div>
  <jsp:doBody/>
  <div id="footer">
    Logged in as ${actionBean.context.request.userPrincipal.name}
    <s:link beanclass="org.cloudme.notepad.stripes.action.logout.LogoutActionBean" class="logout">Sign out</s:link>
  </div>
</body>
<script>
$(document).ready(function() {
  $(".selectOnFocus").focus(function() {
    this.setSelectionRange(0, $(this).val().length);
  });
  $("a").on("click", function(event) {
    event.preventDefault();
    if (!$(this).hasClass("delete") || confirm($(this).attr("title"))) {
      var now = new Date();
      document.location.href = $(this).attr("href") + "?t=" + now.getTime() + "&tzo=" + (now.getTimezoneOffset() / 60);
    }
  });
});
</script>
</html>
