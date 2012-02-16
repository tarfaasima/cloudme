<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<h1>Migration</h1>

<p>Migrated <code>todo</code> attribute of the namespaces:</p>

<ul>
<c:forEach items="${actionBean.namespaces}" var="namespace">
  <li>${namespace}</li>
</c:forEach>
</ul>
