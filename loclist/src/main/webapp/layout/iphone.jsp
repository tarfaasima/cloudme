<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-definition>
<div id="header">
  <h1><s:layout-component name="header"/></h1>
  <div id="buttonLeft"><s:layout-component name="buttonLeft"/></div>
  <div id="buttonRight"><s:layout-component name="buttonRight"/></div>
</div>
<div id="content"><s:layout-component name="content"/></div>
<div id="footer"><s:layout-component name="footer"/></div>
</s:layout-definition>