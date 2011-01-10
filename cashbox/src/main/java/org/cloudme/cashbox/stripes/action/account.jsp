<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="header">Loclist</s:layout-component>
  <s:layout-component name="body">
    <ul>
      <c:forEach items="${actionBean.accounts}" var="account">
        <li>${account.name}</li>
      </c:forEach>
    </ul>
    <s:form action="/action/account/create">
      <div>
        Create new account
      </div>
      <div>
        Name
      </div>
      <div>
        <s:text name="account.name"/>
      </div>
      <div>
        <s:submit name="create"/>
      </div>
    </s:form>
  </s:layout-component>
</s:layout-render>
