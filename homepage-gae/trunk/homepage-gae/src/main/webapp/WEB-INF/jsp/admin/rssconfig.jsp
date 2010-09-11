<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:form beanclass="de.moritzpetersen.homepage.stripes.action.admin.RssConfigActionBean">
  <s:layout-render name="/WEB-INF/jsp/admin/crud_template.jsp" path="rssconfig">
    <s:layout-component name="contents">
      <s:text name="items[${i}].url" />
    </s:layout-component>
  </s:layout-render>
</s:form>
