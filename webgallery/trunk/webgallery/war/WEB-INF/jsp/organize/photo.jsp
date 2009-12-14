<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title>
      Photos - ${actionBean.gallery.name}
    </title>
  </head>
  <body>
    <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.PhotoActionBean">
      <s:hidden name="gallery.id"/>
      <div>
        Upload
      </div>
      <div>
        <s:file name="photoFile"/>
      </div>
      <s:submit name="upload" value="Upload" />
    </s:form>
    <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.PhotoActionBean">
      <s:hidden name="gallery.id"></s:hidden>
      <c:forEach items="${actionBean.allPhotos}" var="photo" varStatus="loop">
        <div>${photo.id}</div>
      </c:forEach>
    </s:form>
  </body>
</html>
