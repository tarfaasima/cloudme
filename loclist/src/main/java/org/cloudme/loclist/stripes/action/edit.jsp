<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/iphone.jsp">
  <s:layout-component name="header">${actionBean.note.name}</s:layout-component>
  <s:layout-component name="buttonRight"><a href="/action/list/checkin/${actionBean.note.id}/" class="checkin">Done</a></s:layout-component>
  <s:layout-component name="content">
    <ul class="edgeToEdge">
      <c:forEach items="${actionBean.noteItems}" var="noteItem">
        <li class="${action}">
          <form action="!/action/edit/${actionBean.note.id}/addOrRemove/${noteItem.itemId}">
            <input type="text" size="5" name="attribute" value="${noteItem.attribute}" placeholder="?" class="attribute"/>
          </form>
          <a href="!/action/edit/${actionBean.note.id}/addOrRemove/${noteItem.itemId}" id="${noteItem.itemId}" class="edit${noteItem.inList ? ' inList' : ''}">
            ${noteItem.text}
          </a>
          <a href="!/action/edit/${actionBean.note.id}/delete/${noteItem.itemId}" title="Do you want to delete ${noteItem.text}?" class="delete">
            Delete
          </a>
        </li>
      </c:forEach>
    </ul>
    <div class="roundedRectangle">
      <div class="formLabel">
        Create a new item
      </div>
      <form action="/action/edit/${actionBean.note.id}/create">
        <div class="inputContainer">
          <input type="text" name="item.text" placeholder="Item" class="create"/>
        </div>
      </form>
      <a href="/action/list/delete/${actionBean.note.id}" class="delete" title="Do you want to delete list ${actionBean.note.name}?"><span>Delete List</span></a>
    </div>
  </s:layout-component>
</s:layout-render>