<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<div id="albums"><div>
<c:forEach items="${actionBean.albums}" var="album"><a href="${(album.id == actionBean.albumId) ? '/' : w:url(album)}" class="album${(album.id == actionBean.albumId) ? " selected" : ""}">${album.name}</a></c:forEach>
</div></div>
