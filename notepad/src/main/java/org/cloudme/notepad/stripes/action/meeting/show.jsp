<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default>
  <jsp:attribute name="title">
    Meeting - ${actionBean.meeting.topic} (<fmt:formatDate value="${actionBean.meeting.date}"/>)
  </jsp:attribute>
  <jsp:attribute name="content">
    <t:notes notes="${actionBean.notes}" />
  </jsp:attribute>
</t:default>
