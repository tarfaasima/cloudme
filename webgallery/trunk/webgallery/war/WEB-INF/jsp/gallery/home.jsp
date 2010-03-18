<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/WEB-INF/layout/gallery.jsp" title="">

<s:layout-component name="headJs">
<script type="text/javascript">
$(function() {
  $(window).bind("resize", updateBackground);
  updateBackground();
});

function updateBackground() {
  var id = "${actionBean.randomPhotoId}";
  if (id != "") {
    var dim = $(document).width() + "x" + $(document).height();
    var img = "/gallery/photo/" + id + "_" + dim + ".jpg";
    $("body").css("background", "url(" + img + ") repeat fixed");
  }
}
</script>
</s:layout-component>

</s:layout-render>
