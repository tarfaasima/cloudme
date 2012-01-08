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
<a href="/_/application">Back</a>
<c:if test="${actionBean.application != null}">
<a href="/_/application/delete/${actionBean.application.id}">Delete</a>
</c:if>
<a href="/_/application/edit">New</a>
<s:form beanclass="org.cloudme.roadmap.stripes.action.application.ApplicationActionBean" method="post">
<s:text name="application.name"/>
<s:hidden name="application.id"/>
<s:submit value="Save" name="save"/>
<s:submit value="Save and exit" name="saveAndExit"/>
</s:form>
<h2>Versions</h2>
<a href="/_/applicationVersion/${actionBean.application.id}">New</a>
<ul>
<c:forEach items="${actionBean.applicationVersions}" var="applicationVersion">
<li>${applicationVersion.version}</li>
</c:forEach>
</ul>
</body>
</html>