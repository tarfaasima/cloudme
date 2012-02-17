<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default title="Todo List">
  <t:notes notes="${actionBean.todos}" />
</t:default>
