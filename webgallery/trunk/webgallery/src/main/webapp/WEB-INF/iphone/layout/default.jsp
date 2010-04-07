<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-definition>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<title>Moritz Petersen &ndash; Photos${title}</title>
<link media="screen" href="/css/iphone.css" type="text/css" rel="stylesheet" />
<link media="screen" href="/css/jquery.lightbox-0.5.css" type="text/css" rel="stylesheet" />
<s:layout-component name="headCss" />
<script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/js/jquery.lightbox-0.5.js"></script>
<script type="text/javascript" src="/js/jquery.tools.min.js"></script>
<script type="text/javascript" src="/js/webgallery.js"></script>
<script type="text/javascript">
$(document).ready(function() {
  $("a.menu").click(function() {
    $("div#menu").toggle();
    $("div#content").toggle();
  });
});
</script>
<s:layout-component name="headJs" />
</head>
<body>
<div id="header">
<div>
<h1><a href="http://moritzpetersen.de">Moritz Petersen</a></h1>
<ul>
  <li><a class="menu">Photos</a></li>
</ul>
</div>
</div>
<div id="menu">
  <ul>
    <c:forEach items="${actionBean.albums}" var="album">
      <li>
        <a href="${(album.id == actionBean.albumId) ? '/' : w:url(album)}" class="${(album.id == actionBean.albumId) ? 'selected' : ''}">${album.name}</a>
      </li>
    </c:forEach>
  </ul>
</div>
<s:messages />
<div id="content">
  <s:layout-component name="content" />
</div>
</body>
</html>
</s:layout-definition>