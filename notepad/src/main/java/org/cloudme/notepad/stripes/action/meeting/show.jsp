<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<s:layout-render name="/WEB-INF/layout/default.jsp">
  <s:layout-component name="title">
    Meeting - ${actionBean.meeting.topic} (<fmt:formatDate value="${actionBean.meeting.date}"/>)
  </s:layout-component>
  <s:layout-component name="content">
    <t:notes notes="${actionBean.notes}" />
  </s:layout-component>
</s:layout-render>
