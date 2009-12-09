<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>
      Webgallery - Organize
    </title>
  </head>
  <body>
    <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.IndexActionBean">
      <c:forEach items="${actionBean.galleryMap}" var="entry">
        <c:set var="gallery" scope="page" value="${entry.value}"/>
        <s:hidden name="galleryMap['${gallery.id}'].id"/>
        <div>
          Name
        </div>
        <div>
          <s:text name="galleryMap['${gallery.id}'].name"/>
          <s:link href="/organize/delete/${gallery.id}">Delete</s:link>
        </div>
        <div>
          Description
        </div>
        <div>
          <s:textarea name="galleryMap['${gallery.id}'].description"/>
        </div>
      </c:forEach>
      <s:submit name="save" value="Save"/>
    </s:form>
  </body>
</html>
