<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="content">
    <div>
      <a href="/action/list/create">Create a new list</a>
    </div>
    <c:forEach items="${actionBean.itemLists}" var="itemList">
      <div>
        <a href="/action/list/show/${itemList.id}">${itemList.name}</a>
      </div>
    </c:forEach>
  </s:layout-component>
</s:layout-render>