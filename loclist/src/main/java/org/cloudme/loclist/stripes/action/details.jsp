<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<s:layout-render name="/layout/iphone.jsp">
<s:layout-component name="title">&#9664; ${actionBean.location.longitude},${actionBean.location.latitude}</s:layout-component>
<s:layout-component name="button"><a href="/action/location">Done</a></s:layout-component>
<s:layout-component name="content">
<ul class="edgetoedge">
<c:forEach var="itemIndex" items="${actionBean.location.itemIndexs}">
<li>
<div>
${itemIndex.text}
</div>
</li>
</c:forEach>
</ul>
<div class="thumbnail"><img src="/action/image/location_${actionBean.location.id}.jpg" class="thumbnail"/></div>
<a href="/action/location/delete/${actionBean.location.id}" class="edgetoedge delete" title="Do you want to discard this location?">Discard location</a>
</s:layout-component>
</s:layout-render>
