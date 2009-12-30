<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Moritz Petersen &ndash; Photos</title>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<link type="text/css" rel="stylesheet" href="/css/lightbox.css"
  media="screen" />
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript"
  src="js/scriptaculous.js?load=effects,builder"></script>
<script type="text/javascript" src="js/lightbox.js"></script>
</head>
<body>
<div id="header">
<div>
<h1><a href="http://moritzpetersen.de">Moritz Petersen</a></h1>
<ul>
  <li class="selected"><a href="/">Photos</a></li>
  <li><a href="http://blog.moritzpetersen.de">Blog</a></li>
  <li><a href="http://blog.cloudme.org">Coding</a></li>
  <li><a href="http://moritzpetersen.de/about.php">About</a></li>
</ul>
</div>
</div>
<div id="content">
<div>
<c:forEach items="${actionBean.photos}" var="photo">
  <div class="thumbnail">
    <a href="/gallery/photo/${photo.id}_l.jpg" rel="lightbox[gallery]" title="${photo.name}">
      <img src="/gallery/photo/${photo.id}_t.jpg" />
    </a>
  </div>
</c:forEach>
</div>
</div>
<div id="footer">
  <a href="/organize/album">organize</a> &ndash; &copy; 2009 by Moritz Petersen
</div>
</body>
</html>
