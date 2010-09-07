<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:errors />
<s:form beanclass="de.moritzpetersen.homepage.stripes.action.admin.RssConfigActionBean">
  <c:forEach items="${actionBean.rssFeeds}" var="rssFeed" varStatus="loop">
    <div>
      <s:hidden name="rssFeeds[${loop.index}].id" value="${rssFeed.id}" />
      <s:text name="rssFeeds[${loop.index}].url" />
      <s:link href="/admin/rssconfig/delete/${rssFeed.id}">Delete</s:link>
    </div>
    <c:set var="newIndex" value="${loop.index + 1}" scope="page" />
  </c:forEach>
  <div>
    <s:text name="rssFeeds[${newIndex}].url" />
  </div>
  <div><s:submit name="save" value="Save" /></div>
</s:form>