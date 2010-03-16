<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-definition>
<c:forEach items="${actionBean.albums}" var="album">
  <li><a href="${(album.id == actionBean.albumId) ? '/' : w:url(album)}" class="${(album.id == actionBean.albumId) ? " selected" : ""}">${album.name}</a></li>
</c:forEach>
</s:layout-definition>
