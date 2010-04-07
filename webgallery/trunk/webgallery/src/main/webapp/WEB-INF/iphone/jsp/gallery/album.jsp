<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-render name="/WEB-INF/iphone/layout/default.jsp" title="${actionBean.album.name}">

<s:layout-component name="content">
  <div class="photos">
    <ul>
    <c:forEach items="${actionBean.photos}" var="photo">
      <li><a href="${w:url(photo)}" title="${photo.name}"><img src="/gallery/photo/${photo.id}_126x126.jpg" alt="${photo.name}"/></a></li>
    </c:forEach>
    </ul>
  </div>
</s:layout-component>

</s:layout-render>
