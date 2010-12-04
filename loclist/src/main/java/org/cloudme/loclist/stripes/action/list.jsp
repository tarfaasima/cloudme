<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/iphone.jsp">
  <s:layout-component name="header">${actionBean.itemList.name}</s:layout-component>
  <s:layout-component name="buttonLeft"><a href="/action/index">Lists</a></s:layout-component>
  <s:layout-component name="buttonRight"><a href="/action/edit/${actionBean.itemList.id}">Edit</a></s:layout-component>
  <s:layout-component name="content">
    <ul class="edgeToEdge">
    <c:forEach var="itemInstance" items="${actionBean.itemInstances}">
      <li>
        <a href="#">
          <c:if test="${not empty itemInstance.attribute}"><span class="attribute">${itemInstance.attribute}</span> </c:if>${itemInstance.text}
        </a>
      </li>
    </c:forEach>
    </ul>
  </s:layout-component>
</s:layout-render>
