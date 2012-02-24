<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default title="Task List (${fn:length(actionBean.todos)})">
  <jsp:attribute name="h1">Task List <span class="date">(${fn:length(actionBean.todos)})</span></jsp:attribute>
  <jsp:body>
    <t:notes notes="${actionBean.todos}" />
    <div id="controls">
      <div class="left">
        <s:link beanclass="org.cloudme.notepad.stripes.action.todo.TodoActionBean" event="export">
          Export
        </s:link>
      </div>
    </div>
    <script>
      $("a.check").on("click", function() {
        if (!$(this).parent().hasClass("done")) {
          $(this).parent().animate({opacity:1}, 10000).fadeOut(100, function() {
            var numberOfTasks = $("li.note").length - $("li.done").length;
            document.title = "Task List (" + numberOfTasks + ")";
            $("h1").html("Task List <span class=\"date\">(" + numberOfTasks + ")</span>");
          });
        }
        else {
          $(this).parent().stop(true, true);
        }
      });
    </script>
  </jsp:body>
</t:default>
