<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title="">
<s:layout-component name="headCss">
</s:layout-component>
<s:layout-component name="headJs">
<script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
  $(window).bind("resize", updateBackground);
  $(".albumheader").click(function () {
	  $(".albums").slideToggle();
  });
  $(".albums").hide();
  updateBackground();
});

function updateBackground() {
  var id = "${actionBean.randomPhotoId}";
  var dim = $(document).width() + "x" + $(document).height();
  var img = "/gallery/photo/" + id + "_" + dim + ".jpg";
  $("body").css("background", "url(" + img + ") repeat fixed");
}
</script>
</s:layout-component>
<s:layout-component name="content">
<div class="albumheader">Albums</div>
<div class="albums">
<c:forEach items="${actionBean.albums}" var="album">
  	<a href="#" class="albumtitle" style="background-image: url(/gallery/photo/${album.photos[0].id}_894x210.jpg); height: 210px">
  	${album.name}
  	<div>${album.description}</div>
  	</a>
<% /* 
</h2>
	<img src="/gallery/photo/${album.photos[0].id}_894x105.jpg"/>
	*/ %>
</c:forEach>
</div>
</s:layout-component>
<s:layout-component name="footerLink">
<a href="/organize/album">organize</a>
</s:layout-component>
</s:layout-render>
