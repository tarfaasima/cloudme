<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/iphone.jsp">
  <s:layout-component name="header">${actionBean.itemList.name}</s:layout-component>
  <s:layout-component name="buttonRight"><a href="/action/list/checkin/${actionBean.itemList.id}/" class="checkin">Done</a></s:layout-component>
  <s:layout-component name="content">
    <ul class="edgeToEdge">
      <c:forEach items="${actionBean.itemInstances}" var="itemInstance">
        <c:set var="action" value="${itemInstance.inList ? 'remove' : 'add'}" />
        <li class="${action}">
          <form action="!/action/edit/${actionBean.itemList.id}/add/${itemInstance.itemId}">
            <input type="text" size="5" name="attribute" value="${itemInstance.attribute}" placeholder="?"/>
          </form>
          <a href="!/action/edit/${actionBean.itemList.id}/${action}/${itemInstance.itemId}" id="${itemInstance.itemId}" class="edit">
            ${itemInstance.text}
          </a>
          <a href="!/action/edit/${actionBean.itemList.id}/delete/${itemInstance.itemId}" title="Do you want to delete ${itemInstance.text}?" class="delete">
            Delete
          </a>
        </li>
      </c:forEach>
    </ul>
    <div class="roundedRectangle">
      <div class="formLabel">
        Create a new item
      </div>
      <form action="/action/edit/${actionBean.itemList.id}/create">
        <div class="inputContainer">
          <input type="text" name="item.text" placeholder="Item" class="create"/>
        </div>
      </form>
      <a href="/action/list/delete/${actionBean.itemList.id}" class="delete" title="Do you want to delete list ${actionBean.itemList.name}?"><span>Delete List</span></a>
    </div>
  </s:layout-component>
</s:layout-render>