<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-definition>
  <s:layout-render name="/WEB-INF/layout/default.jsp">
  
    <s:layout-component name="title"> &ndash; Organize &ndash; ${title}</s:layout-component>
  
    <s:layout-component name="headCss">${headCss}</s:layout-component>

    <s:layout-component name="headJs">${headJs}</s:layout-component>

    <s:layout-component name="menu">
		  <li><a href="/">Home</a></li>
		  <li><s:link beanclass="org.cloudme.webgallery.stripes.action.organize.AlbumActionBean">Albums</s:link></li>
		  <li><s:link beanclass="org.cloudme.webgallery.stripes.action.organize.SettingsActionBean">Settings</s:link></li>
    </s:layout-component>

		<s:layout-component name="content">${content}</s:layout-component>

		<s:layout-component name="footerLink">
			<s:link beanclass="org.cloudme.webgallery.stripes.action.LogoutActionBean">logout</s:link>
		</s:layout-component>

  </s:layout-render>
</s:layout-definition>