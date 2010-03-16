<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title=" - ${actionBean.album.name}">
<s:layout-component name="menu">
  <s:layout-render name="/WEB-INF/layout/albumMenu.jsp" />
</s:layout-component>
<s:layout-component name="content">
<div class="photos">
<ul>
<c:forEach items="${actionBean.photos}" var="photo">
  <li><a href="/gallery/photo/${photo.id}_l.jpg" title="${photo.name}" class="lightbox"><img src="/gallery/photo/${photo.id}_m.jpg" alt="${photo.name}"/></a><div class="tooltip">${photo.name}<a href="${w:url(photo)}">view</a></div></li>
</c:forEach>
</ul>
</div>
</s:layout-component>
<s:layout-component name="footerLink">
<a href="/organize/album">organize</a>
</s:layout-component>
</s:layout-render>
