<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-definition>
  <s:errors />
  <c:forEach items="${actionBean.items}" var="item" varStatus="loop">
    <c:set var="i" value="${loop.index}" />
    <s:hidden name="items[${i}].id" value="${item.id}" />
    <div>
      <s:layout-component name="contents" />
      <s:link href="/admin/${path}/delete/${item.id}">Delete</s:link>
    </div>
  </c:forEach>
  <div>
    <c:set var="i" value="${f:length(actionBean.items)}" />
    <s:layout-component name="contents" />
  </div>
  <div><s:submit name="save" value="Save" /></div>
</s:layout-definition>
