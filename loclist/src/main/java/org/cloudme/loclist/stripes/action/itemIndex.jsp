<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="content">
    <c:forEach items="${actionBean.items}" var="item">
      <div>
        <a href="/action/item/${actionBean.itemListId}/add/${item.id}">${item.text}</a>
      </div>
    </c:forEach>
    <div>
      <s:form beanclass="org.cloudme.loclist.stripes.action.ItemActionBean">
        <s:text name="item.text"></s:text>
        <s:submit name="save" value="Save"></s:submit>
      </s:form>
    </div>
  </s:layout-component>
</s:layout-render>