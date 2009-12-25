<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title>
      Photos - ${actionBean.album.name}
    </title>
  </head>
  <body>
    <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.PhotoActionBean">
      <s:hidden name="albumId" value="${actionBean.album.id}"/>
      <div>
        Upload
      </div>
      <div>
        <s:file name="photoFile"/>
      </div>
      <s:submit name="upload" value="Upload" />
    </s:form>
    <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.PhotoActionBean">
      <s:hidden name="albumId" value="${actionBean.album.id}"/>
      <c:forEach items="${actionBean.album.photos}" var="item" varStatus="loop">
      </c:forEach>
      <s:submit name="save" value="Save" />
    </s:form>
  </body>
</html>
