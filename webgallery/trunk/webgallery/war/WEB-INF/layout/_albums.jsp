<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<div id="menu">
<ul>
<c:forEach items="${actionBean.albums}" var="album">
<li><a href="${(album.id == actionBean.albumId) ? '/' : w:url(album)}" class="${(album.id == actionBean.albumId) ? " selected" : ""}">${album.name}</a></li>
</c:forEach>
</ul>
</div>
