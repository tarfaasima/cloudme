<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title="">
<s:layout-component name="headCss">
<base href="/"/>
<link rel="stylesheet" type="text/css" href="/css/jquery.lightbox-0.5.css" media="screen" />
</s:layout-component>
<s:layout-component name="headJs">
<script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/js/jquery.lightbox-0.5.js"></script>
<script type="text/javascript">
$(function() {
    $('div#photos a').lightBox();
});
</script>
</s:layout-component>
<s:layout-component name="content">
<div id="albums">
<ul>
<c:forEach items="${actionBean.albums}" var="album">
<li><a href="${(album.id == actionBean.albumId) ? '/' : w:url(album)}" class="album${(album.id == actionBean.albumId) ? " selected" : ""}">${album.name}</a></li>
</c:forEach>
</ul>
</div>
<div id="photos">
<c:forEach items="${actionBean.photos}" var="photo"><a href="/gallery/photo/${photo.id}_l.jpg"><img src="/gallery/photo/${photo.id}_194x194.jpg"/></a></c:forEach>
</div>
</s:layout-component>
<s:layout-component name="footerLink">
<a href="/organize/album">organize</a>
</s:layout-component>
</s:layout-render>
