<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="body">
    <s:form beanclass="org.cloudme.taskflow.stripes.action.TaskActionBean">
      <div>
        <s:text name="task.title" placeholder="Title"/>
      </div>
      <div>
        <s:textarea name="task.content" placeholder="Content"/>
      </div>
      <div>
        <s:text name="task.dueDate" placeholder="Due date MM.DD.YYYY"/>
      </div>
      <div>
        <s:text name="task.priority" placeholder="Priority"/>
      </div>
      <div>
        <s:text name="task.tags" placeholder="Tags (comma separated)"/>
      </div>
      <div>
        <s:submit name="create" value="Create Task"/>
      </div>
    </s:form>
  </s:layout-component>
</s:layout-render>