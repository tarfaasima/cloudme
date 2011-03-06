<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-definition>
<div id="header">
  <div id="title"><a href="/action/index"><s:layout-component name="title"/></a></div>
  <div id="button"><s:layout-component name="button"/></div>
</div>
<div id="content"><s:layout-component name="content"/></div>
<div id="footer"><s:layout-component name="footer"/></div>
</s:layout-definition>