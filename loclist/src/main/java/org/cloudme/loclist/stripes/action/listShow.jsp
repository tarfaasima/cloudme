<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="content">
    <div>
      <a href="/action/list/index">Lists</a>
    </div>
    <div>
      ${actionBean.itemList.name}
    </div>
  </s:layout-component>
</s:layout-render>