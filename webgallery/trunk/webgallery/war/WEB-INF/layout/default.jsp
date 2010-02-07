<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-definition>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Moritz Petersen &ndash; Photos ${title}</title>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<s:layout-component name="headCss" />
<s:layout-component name="headJs" />
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
  <s:layout-component name="content" />
</div>
<div id="footer">
  ${footerLink} &bull; 
  &copy; ${w:copyrightYear("2008", "-")} by Moritz Petersen &bull; 
  <a href="http://appengine.google.com/a/cloudme.org">v.${w:appVersion("WEB-INF/appengine-web.xml")}</a> &bull; 
  <a href="http://formspring.me/moritzpetersen" title="Ask me on Formspring.me" rel="nofollow">ask me</a>
</div>
</body>
</html>
</s:layout-definition>