<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title="">
<s:layout-component name="headCss">
<link type="text/css" rel="stylesheet" href="/css/lightbox.css" media="screen" />
</s:layout-component>
<s:layout-component name="headJs">
<script type="text/javascript" src="/js/prototype.js"></script>
<script type="text/javascript" src="/js/scriptaculous.js?load=effects,builder"></script>
<script type="text/javascript" src="/js/lightbox.js"></script>
</s:layout-component>
<s:layout-component name="content">
<div>
<c:forEach items="${actionBean.photos}" var="photo">
  <div class="thumbnail">
    <a href="/gallery/photo/${photo.id}_l.jpg" rel="lightbox[gallery]" title="${photo.name}">
      <img src="/gallery/photo/${photo.id}_t.jpg" />
    </a>
  </div>
</c:forEach>
</div>
</s:layout-component>
<s:layout-component name="footerLink">
<a href="/organize/album">organize</a>
</s:layout-component>
</s:layout-render>
