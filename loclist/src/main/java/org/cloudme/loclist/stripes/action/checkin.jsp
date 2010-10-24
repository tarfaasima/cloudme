<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

Item Instances
<c:forEach var="itemInstance" items="${actionBean.itemInstances}">
  <div>
    ${itemInstance.text}
  </div>
</c:forEach>