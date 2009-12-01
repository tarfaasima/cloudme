<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Webgallery - Admin</title>
  </head>
  <body>
    <h1>Galleries</h1>
    <s:link beanclass="org.cloudme.webgallery.stripes.action.admin.AdminEditActionBean">create new gallery</s:link>
    <c:forEach items="${actionBean.galleryList}" var="gallery">
      ${gallery.name}
    </c:forEach>
  </body>
</html>
