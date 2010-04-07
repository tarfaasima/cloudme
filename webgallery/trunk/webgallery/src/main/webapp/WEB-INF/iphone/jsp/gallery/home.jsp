<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/WEB-INF/iphone/layout/default.jsp">

<s:layout-component name="headJs">
<script type="text/javascript">
$(function() {
  var id = "${actionBean.randomPhotoId}";
  updateBackground(id);
});
</script>
</s:layout-component>

</s:layout-render>
