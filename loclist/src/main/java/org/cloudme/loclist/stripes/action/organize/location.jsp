<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.5.2.min.js"></script>
<title>Lists</title>
</head>
<body>
<c:forEach items="${actionBean.locations}" var="location">
<div>
<h2>${location.latitude},${location.longitude}</h2>
<img src="http://maps.google.com/maps/api/staticmap?center=${location.latitude},${location.longitude}&zoom=15&size=256x256&maptype=hybrid&markers=color:red|${location.latitude},${location.longitude}&sensor=false">
<ul>
<c:forEach items="${location.itemIndexs}" var="itemIndex">
<li>${itemIndex.text} (${itemIndex.lastUpdateString})</li>
</c:forEach>
</ul>
</div>
</c:forEach>
</body>
</html>
