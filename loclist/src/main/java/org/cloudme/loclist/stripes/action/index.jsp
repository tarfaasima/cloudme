<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<s:layout-render name="/layout/iphone.jsp">
  <s:layout-component name="title">Lists</s:layout-component>
  <s:layout-component name="content">
    <ul class="edgeToEdge">
      <c:forEach items="${actionBean.notes}" var="note">
        <li><a href="/action/note/checkin/${note.id}/" class="checkin">${note.name}</a></li>
      </c:forEach>
    </ul>
    <form action="/action/index/create" class="edgeToEdge">
      <input type="text" name="note.name" placeholder="Name of note" class="submit"/>
    </form>
  </s:layout-component>
</s:layout-render>
