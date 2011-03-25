<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/iphone.jsp">
<s:layout-component name="title">${actionBean.note.name}</s:layout-component>
<s:layout-component name="button"><a href="/action/note/checkin/${actionBean.note.id}/" class="checkin">Done</a></s:layout-component>
<s:layout-component name="content">
<ul class="edgetoedge">
<c:forEach items="${actionBean.noteItems}" var="noteItem">
<li class="${action}">
<form action="!/action/edit/${actionBean.note.id}/addOrRemove/${noteItem.itemId}">
<input type="text" size="5" name="attribute" value="${noteItem.attribute}" placeholder="?" class="attribute submit"/>
</form>
<a href="!/action/edit/${actionBean.note.id}/delete/${noteItem.itemId}" title="Do you want to delete ${noteItem.text}?" class="button delete">
Delete
</a>
<a href="!/action/edit/${actionBean.note.id}/addOrRemove/${noteItem.itemId}" id="${noteItem.itemId}" class="edit${noteItem.inNote ? ' inNote' : ''}">
${noteItem.text}
</a>
</li>
</c:forEach>
</ul>
<form action="/action/edit/${actionBean.note.id}/create" class="edgetoedge">
<input type="text" name="item.text" placeholder="Create a new item" class="submit"/>
</form>
<a href="/action/note/delete/${actionBean.note.id}" class="edgetoedge delete" title="Do you want to delete list ${actionBean.note.name}?">Delete list</a>
</s:layout-component>
</s:layout-render>