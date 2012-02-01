<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<s:layout-definition>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="apple-touch-icon" href="/apple-touch-icon-114x114.png" />
<meta name="viewport"
  content="width = device-width, initial-scale = 1, minimum-scale = 1, maximum-scale = 1, user-scalable = no" />
<title>Notepad - ${title}</title>
<link href="/css/notepad.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.7.1.min.js"></script>
<script src="/js/jquery-ui-1.8.17.custom.min.js"></script>
<script>
$(document).ready(function() {
  $(".selectOnFocus").focus(function() {
    this.setSelectionRange(0, $(this).val().length);
  });
  $("a").on("click", function(event) {
    event.preventDefault();
    if (!$(this).hasClass("delete") || confirm($(this).attr("title"))) {
      document.location.href = $(this).attr("href");
    }
  });
});
</script>
<script>
<s:layout-component name="javascript" />
</script>
</head>
<body>
  <div id="menu">
    <s:link beanclass="org.cloudme.notepad.stripes.action.meeting.MeetingActionBean">Meetings</s:link>
    <s:link beanclass="org.cloudme.notepad.stripes.action.note.NoteActionBean" event="create">Note</s:link>
    <a href="#">To-Do</a>
  </div>
  <div id="header">
    <h1>${title}</h1>
  </div>
  <s:layout-component name="content" />
  <div id="footer">
    Logged in as ${actionBean.context.request.userPrincipal.name}
    <s:link beanclass="org.cloudme.notepad.stripes.action.logout.LogoutActionBean" class="logout">Sign out</s:link>
  </div>
</body>
</html>
</s:layout-definition>