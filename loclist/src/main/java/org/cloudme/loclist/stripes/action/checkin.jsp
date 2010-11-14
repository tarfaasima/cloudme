<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="edgeToEdge">
<c:forEach var="itemInstance" items="${actionBean.itemInstances}">
  <li>
    <a href="#">
      <c:if test="${not empty itemInstance.attribute}"><span class="attribute">${itemInstance.attribute}</span> </c:if>${itemInstance.text}
    </a>
  </li>
</c:forEach>
</ul>