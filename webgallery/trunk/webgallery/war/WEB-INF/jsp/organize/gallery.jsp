<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>
      Webgallery - Organize
    </title>
  </head>
  <body>
    <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.GalleryActionBean">
      <c:forEach items="${actionBean.items}" var="item" varStatus="loop">
        <s:hidden name="items[${loop.index}].id"/>
        <div>
          Name
        </div>
        <div>
          <s:text name="items[${loop.index}].name"/>
          <s:link href="/organize/gallery/delete/${item.id}">
            Delete
          </s:link>
        </div>
        <div>
          Description
        </div>
        <div>
          <s:textarea name="items[${loop.index}].description"/>
        </div>
      </c:forEach>
      <div>
        Name
      </div>
      <div>
        <s:text name="items[${f:length(actionBean.items)}].name"/>
      </div>
      <s:submit name="save" value="Save"/>
      <s:reset name="reset" value="Reset"/>
    </s:form>
  </body>
</html>
