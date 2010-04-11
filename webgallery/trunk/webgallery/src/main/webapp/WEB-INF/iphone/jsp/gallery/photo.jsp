<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-render name="/WEB-INF/iphone/layout/base.jsp" title="${actionBean.photo.name}">

<s:layout-component name="headJs">
<script type="text/javascript">
$(function() {
  var id = "${actionBean.photoId}";
  var width = $(document).width();
  var height = $(document).height();
  var dim = width + "#" + height;
	var src = "/gallery/photo/" + id + "_" + dim + ".jpg";
	$("div.fullscreen").css({
	  "width": width + "px",
	  "height": height + "px"
	});
	$(".fullscreen img").attr("src", src);
});
</script>
</s:layout-component>

<s:layout-component name="body">
	<div class="fullscreen">
	  <a href="/gallery/album/${actionBean.albumId}"><img src=""/></a>
	</div>
</s:layout-component>

</s:layout-render>
