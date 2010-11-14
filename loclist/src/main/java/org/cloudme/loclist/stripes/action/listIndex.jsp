<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <ul class="edgeToEdge">
      <c:forEach items="${actionBean.itemLists}" var="itemList">
        <li>
          <a href="${itemList.id}" class="checkin">${itemList.name}</a>
        </li>
      </c:forEach>
    </ul>
