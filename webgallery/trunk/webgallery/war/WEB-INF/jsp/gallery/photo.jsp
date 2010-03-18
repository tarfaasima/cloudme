<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-render name="/WEB-INF/layout/gallery.jsp" title=" - ${actionBean.photo.name}">

<s:layout-component name="content">
<div class="photos">
<ul>
  <li><a href="/gallery/album/${actionBean.albumId}"><img src="/gallery/photo/${actionBean.photoId}_l.jpg"/></a></li>
</ul>
</div>
<div class="description">
  <p>${actionBean.photo.name}</p>
</div>
<div class="photos">
<ul>
<c:forEach items="${actionBean.photos}" var="photo">
  <li><a href="/gallery/album/${actionBean.albumId}/photo/${photo.id}"><img src="/gallery/photo/${photo.id}_s.jpg"/></a></li>
</c:forEach>
</ul>
</div>
</s:layout-component>

</s:layout-render>
