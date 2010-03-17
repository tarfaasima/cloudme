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
<title>Moritz Petersen &ndash; Photos ${title}</title>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/css/jquery.lightbox-0.5.css" media="screen" />
<s:layout-component name="headCss" />
<script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/js/jquery.lightbox-0.5.js"></script>
<script type="text/javascript" src="/js/jquery.tools.min.js"></script>
<script type="text/javascript">
$(function() {
    $('a.lightbox').lightBox({
        imageLoading:           '/images/lightbox/lightbox-ico-loading.gif',
        imageBtnPrev:           '/images/lightbox/lightbox-btn-prev.gif',
        imageBtnNext:           '/images/lightbox/lightbox-btn-next.gif',
        imageBtnClose:          '/images/lightbox/lightbox-btn-close.gif',
        imageBlank:             '/images/lightbox/lightbox-blank.gif',
        containerResizeSpeed:   200,
        txtImage:               'Photo'
    });
});
$(document).ready(function() {
    $('.photos img').tooltip({
        tip:      '.tooltip',
        position: 'bottom center'
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
  <li><a href="/" class="selected">Photos</a></li>
  <li><a href="http://blog.moritzpetersen.de">Blog</a></li>
  <li><a href="http://blog.cloudme.org">Coding</a></li>
  <li><a href="http://moritzpetersen.de/about.php">About</a></li>
</ul>
</div>
</div>
<div id="menu">
  <ul>
    <s:layout-component name="menu" />
  </ul>
</div>
<s:messages />
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