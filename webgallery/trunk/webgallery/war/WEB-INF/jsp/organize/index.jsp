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
    <c:forEach items="${actionBean.galleryList}" var="gallery">
      <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.IndexActionBean">
        <div>
          Name
        </div>
        <div>
          <s:text name="gallery.name" value="${gallery.name}"/>
        </div>
        <div>
          Description
        </div>
        <div>
          <s:textarea name="gallery.description" value="${gallery.description}"/>
        </div>
        <s:hidden name="gallery.id" value="${gallery.id}"/>
        <div>
          <s:submit name="save" value="Update"/>
          <s:reset name="reset" value="Reset"/>
          <s:submit name="delete" value="Delete"/>
        </div>
      </s:form>
      <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.PhotoActionBean">
        <div>
          Upload
        </div>
        <div>
          <s:file name="photoFile"/>
        </div>
        <s:hidden name="galleryId" value="${gallery.id}"/>
        <div>
          <s:submit name="upload" value="Upload"/>
        </div>
      </s:form>
    </c:forEach>
    <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.IndexActionBean">
      <div>
        Name
      </div>
      <div>
          <s:text name="gallery.name"/>
      </div>
      <div>
        <s:submit name="save" value="Create"/>
      </div>
    </s:form>
  </body>
</html>
