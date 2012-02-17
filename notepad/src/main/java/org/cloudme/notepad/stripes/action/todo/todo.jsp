<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default title="Todo List (${fn:length(actionBean.todos)})">
  <jsp:attribute name="h1">Todo List <span class="date">(${fn:length(actionBean.todos)})</span></jsp:attribute>
  <jsp:body>
    <t:notes notes="${actionBean.todos}" />
  </jsp:body>
</t:default>
