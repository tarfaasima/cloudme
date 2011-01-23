<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<option value="-1"></option>
<c:forEach items="${actionBean.accounts}" var="account">
  <option value="${account.id}">${account.name}</option>
</c:forEach>