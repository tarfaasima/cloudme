<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>

<s:errors/>
<s:form beanclass="de.moritzpetersen.homepage.stripes.action.admin.RssConfigActionBean">
  <c:forEach items="rssFeeds" var="rssFeed" varStatus="loop">
    <div>
      <s:hidden name="rssFeeds[${loop.index}].id" value="${rssFeed.id}" />
      <s:text name="rssFeeds[${loop.index}].url" />
    </div>
  </c:forEach>
  <div><s:submit name="save" value="Save" /></div>
</s:form>