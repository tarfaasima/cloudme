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
      <c:forEach items="${actionBean.galleries}" var="gallery" varStatus="loop">
        <s:hidden name="galleries[${loop.index}].id"/>
        <div>
          Name
        </div>
        <div>
          <s:text name="galleries[${loop.index}].name"/>
          <s:link href="/organize/gallery/delete/${gallery.id}">Delete</s:link>
        </div>
        <div>
          Description
        </div>
        <div>
          <s:textarea name="galleries[${loop.index}].description"/>
        </div>
        <c:set var="newIndex" value="${loop.index + 1}" scope="page"/>
      </c:forEach>
        <div>
          Name
        </div>
        <div>
          <s:text name="galleries[${newIndex}].name"/>
        </div>
        <div>
          Description
        </div>
        <div>
          <s:textarea name="galleryMap[${newIndex}].description"/>
        </div>
      <s:submit name="save" value="Save"/>
    </s:form>
  </body>
</html>
