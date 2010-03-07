<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<s:layout-render name="/WEB-INF/layout/default.jsp" title=" - Settings">
<s:layout-component name="headJs">
<script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
$(function() {
    $('a#close').click(function() {
        $('.messages').fadeOut("slow");
    });
    $('.messages').delay(5000).fadeOut("slow");
});
</script>
</s:layout-component>
<s:layout-component name="content">
<jsp:include page="/WEB-INF/layout/_menu.jsp"/>
<s:messages/>
<s:form beanclass="org.cloudme.webgallery.stripes.action.organize.SettingsActionBean">
<s:hidden name="metaData.id" />
<div>Flickr</div>
<div>Key:</div>
<div><s:text name="metaData.key" /></div>
<div>Secret:</div>
<div><s:text name="metaData.secret" /></div>
<div><a href="${actionBean.flickrLoginLink}">Authenticate</a></div>
<div><s:submit value="Save" name="save"/></div>
</s:form>
</s:layout-component>
<s:layout-component name="footerLink">
<s:link beanclass="org.cloudme.webgallery.stripes.action.LogoutActionBean">logout</s:link>
</s:layout-component>
</s:layout-render>
