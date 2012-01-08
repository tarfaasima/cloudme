<%@page import="org.cloudme.roadmap.stripes.action.application.ApplicationActionBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Application Version</title>
</head>
<body>
<h1>Application Version</h1>
<a href="/_/application/edit/${actionBean.applicationVersion.applicationId}">Back</a>
<c:if test="${actionBean.applicationVersion != null}">
<a href="/_/applicationVersion/${actionBean.applicationVersion.applicationId}/delete/${actionBean.applicationVersion.id}">Delete</a>
</c:if>
<s:form beanclass="org.cloudme.roadmap.stripes.action.application.version.ApplicationVersionActionBean" method="post">
<s:text name="applicationVersion.version"/>
<s:hidden name="applicationVersion.id"/>
<s:hidden name="applicationVersion.applicationId"/>
<s:submit value="Save" name="save"/>
<s:submit value="Save and exit" name="saveAndExit"/>
</s:form>
</body>
</html>