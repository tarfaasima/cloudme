<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/iphone.jsp">
  <s:layout-component name="header">${actionBean.note.name}</s:layout-component>
  <s:layout-component name="buttonLeft"><a href="/action/index">Lists</a></s:layout-component>
  <s:layout-component name="buttonRight"><a href="/action/edit/${actionBean.note.id}">Edit</a></s:layout-component>
  <s:layout-component name="content">
    <ul class="edgeToEdge">
    <c:forEach var="noteItem" items="${actionBean.noteItems}">
      <li>
        <a href="!/action/item/tick/${actionBean.checkinId}/${noteItem.id}" class="note${noteItem.ticked ? ' ticked' : ''}">
          <c:if test="${not empty noteItem.attribute}"><span class="attribute">${noteItem.attribute}</span> </c:if>${noteItem.text}
        </a>
      </li>
    </c:forEach>
    </ul>
    ${actionBean.longitude}, ${actionBean.latitude}
  </s:layout-component>
</s:layout-render>
