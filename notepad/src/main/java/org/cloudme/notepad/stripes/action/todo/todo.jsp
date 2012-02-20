<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
  </jsp:body>
</t:default>
