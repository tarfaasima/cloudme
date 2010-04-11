<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-definition>
  <s:layout-render name="/WEB-INF/iphone/layout/base.jsp">
    <s:layout-component name="title">${empty title ? '' : ' &ndash; '}${title}</s:layout-component>
    <s:layout-component name="headCss">
      <link media="screen" href="/css/jquery.lightbox-0.5.css" type="text/css" rel="stylesheet" />
      ${headCss}
    </s:layout-component>
    <s:layout-component name="headJs">
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
      ${headJs}
    </s:layout-component>
    <s:layout-component name="body">
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
        ${content}
			</div>
    </s:layout-component>
  </s:layout-render>
</s:layout-definition>