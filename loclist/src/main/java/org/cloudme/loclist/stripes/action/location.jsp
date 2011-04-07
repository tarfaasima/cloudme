<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<s:layout-render name="/layout/iphone.jsp">
<s:layout-component name="title">&#9664; Locations</s:layout-component>
<s:layout-component name="button"><a href="/action/index">Done</a></s:layout-component>
<s:layout-component name="content">
<ul class="edgetoedge">
<c:forEach var="location" items="${actionBean.locations}">
<li>
<a href="/action/location/details/${location.id}" class="thumbnail">
<img src="/action/image/location_${location.id}.jpg" class="thumbnail"/>
${location.longitude},${location.latitude} 
<span class="details">(${fn:length(location.itemIndexs)} items)</span>
</a>
</li>
</c:forEach>
</ul>
</s:layout-component>
</s:layout-render>
