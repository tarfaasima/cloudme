<%@page import="org.cloudme.roadmap.stripes.action.application.ApplicationActionBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Application</title>
</head>
<body>
<h1>Application</h1>
<a href="/_/application/edit">New</a>
<ul>
<c:forEach items="${actionBean.applications}" var="application">
<li><a href="/_/application/edit/${application.id}">${application.name}</a></li>
</c:forEach>
</ul>
</body>
</html>