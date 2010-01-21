<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title="">
<s:layout-component name="headCss">
</s:layout-component>
<s:layout-component name="headJs">
</s:layout-component>
<s:layout-component name="content">
<div id="albums">
<ul>
<c:forEach items="${actionBean.albums}" var="album">
<li>
  	<a href="/gallery/album/${album.id}" class="album${(album.id == actionBean.albumId) ? " selected" : ""}">${album.name}</a>
</li>
</c:forEach>
</ul>
</div>
<div id="photos">
<ul>
<c:forEach items="${actionBean.photos}" var="photo">
<li>
	<img src="/gallery/photo/${photo.id}_210x210.jpg"/>
</li>
</c:forEach>
</ul>
</div>
</s:layout-component>
<s:layout-component name="footerLink">
<a href="/organize/album">organize</a>
</s:layout-component>
</s:layout-render>
