<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-definition>
  <s:layout-render name="/WEB-INF/default/layout/default.jsp">

	<s:layout-component name="title">${empty title ? '' : ' &ndash; '}${title}</s:layout-component>
	
	<s:layout-component name="headCss">${headCss}</s:layout-component>
	
	<s:layout-component name="headJs">${headJs}</s:layout-component>
	
	<s:layout-component name="menu">
	  <c:forEach items="${actionBean.albums}" var="album">
    <li>
	    <a href="${(album.id == actionBean.albumId) ? '/' : w:url(album)}" class="${(album.id == actionBean.albumId) ? 'selected' : ''}">${album.name}</a>
	  </li>
    </c:forEach>
  </s:layout-component>

  <s:layout-component name="content">${content}</s:layout-component>

  <s:layout-component name="footerLink"><a href="/organize/album">organize</a></s:layout-component>
</s:layout-render>
</s:layout-definition>
