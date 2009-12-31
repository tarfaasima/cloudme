<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title="">
<s:layout-component name="content">
    <s:link beanclass="org.cloudme.webgallery.stripes.action.organize.AlbumActionBean">
      Albums
    </s:link>
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
      <c:forEach items="${actionBean.album.photos}" var="photo" varStatus="loop">
        <div>
          <img src="/gallery/photo/${photo.id}_t.jpg"/>
        </div>
        <div>
          <s:text name="album.photos[${loop.index}].name"/>
        </div>
        <div>
          <s:link href="/organize/photo/${actionBean.album.id}/delete/${photo.id}">delete</s:link>
        </div>
      </c:forEach>
      <s:submit name="save" value="Save" />
    </s:form>
</s:layout-component>
<s:layout-component name="footerLink">
<s:link beanclass="org.cloudme.webgallery.stripes.action.LogoutActionBean">logout</s:link>
</s:layout-component>
</s:layout-render>
