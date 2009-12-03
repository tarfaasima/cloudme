<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Admin - Gallery - Edit</title>
</head>
<body>
<h1>Edit</h1>
<s:form beanclass="org.cloudme.webgallery.stripes.action.admin.AdminEditActionBean">
<s:hidden name="gallery.id"/>
<s:text name="gallery.name"/>
<s:textarea name="gallery.description"/>
<s:submit name="save" value="Save"/>
</s:form>
</body>
</html>
