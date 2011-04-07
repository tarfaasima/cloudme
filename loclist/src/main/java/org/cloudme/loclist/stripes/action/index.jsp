<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<s:layout-render name="/layout/iphone.jsp">
<s:layout-component name="title">Lists</s:layout-component>
<s:layout-component name="button"><a href="/action/location">Locations</a></s:layout-component>
<s:layout-component name="content">
<ul class="edgetoedge">
<c:forEach items="${actionBean.notes}" var="note">
<li><a href="/action/note/checkin/${note.id}/" class="checkin">${note.name}</a></li>
</c:forEach>
</ul>
<form action="/action/index/create" class="edgetoedge">
<input type="text" name="note.name" placeholder="Create a new list" class="submit"/>
</form>
</s:layout-component>
</s:layout-render>
