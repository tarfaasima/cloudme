<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/iphone.jsp">
  <s:layout-component name="header">${actionBean.itemList.name}</s:layout-component>
  <s:layout-component name="buttonRight"><a href="#">Done</a></s:layout-component>
  <s:layout-component name="content">
    <ul class="edgeToEdge">
      <c:forEach items="${actionBean.itemInstances}" var="itemInstance">
        <c:set var="removeUrl" value="/action/edit/${actionBean.itemList.id}/remove/${itemInstance.itemId}" />
        <c:set var="addUrl" value="/action/edit/${actionBean.itemList.id}/add/${itemInstance.itemId}" />
        <li class="${itemInstance.inList ? 'remove' : 'add'}">
          <form action="${addUrl}">
            <input type="text" size="5" name="attribute" value="${itemInstance.attribute}" placeholder="?"/>
          </form>
          <a href="${itemInstance.inList ? removeUrl : addUrl}" id="${itemInstance.itemId}" class="toggle">
            ${itemInstance.text}
          </a>
          <a href="Do you want to delete ${itemInstance.text}?" class="delete">
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