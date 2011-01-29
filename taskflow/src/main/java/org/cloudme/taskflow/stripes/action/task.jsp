<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="body">
    <ul>
      <c:forEach items="${actionBean.tasks}" var="task">
        <li>
          ${task.title}
        </li>
      </c:forEach>
    </ul>
    <s:form beanclass="org.cloudme.taskflow.stripes.action.TaskActionBean">
      <div>
        Enter new task here:
      </div>
      <div>
        Title
      </div>
      <div>
        <s:text name="task.title" placeholder="Title"/>
      </div>
      <div>
        Content
      </div>
      <div>
        <s:textarea name="task.content" placeholder="Content"/>
      </div>
      <div>
        Due date
      </div>
      <div>
        <s:text name="task.dueDate" placeholder="Due date MM.DD.YYYY"/>
      </div>
      <div>
        Priority
      </div>
      <div>
        <s:text name="task.priority" placeholder="Priority"/>
      </div>
      <div>
        Tags <small>comma separated</small>
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