<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<s:layout-render name="/WEB-INF/layout/default.jsp" title="">
<s:layout-component name="content">
<div>
    <s:form beanclass="org.cloudme.webgallery.stripes.action.organize.AlbumActionBean">
      <c:forEach items="${actionBean.items}" var="item" varStatus="loop">
        <s:hidden name="items[${loop.index}].id"/>
        <div>
          Name
        </div>
        <div>
          <s:text name="items[${loop.index}].name"/>
          <s:link href="/organize/album/delete/${item.id}">
            Delete
          </s:link>
          <s:link href="/organize/photo/${item.id}">
            Photos (${f:length(item.photos)})
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
</div>
</s:layout-component>
<s:layout-component name="footerLink">
<a href="/logout">logout</a>
</s:layout-component>
</s:layout-render>
