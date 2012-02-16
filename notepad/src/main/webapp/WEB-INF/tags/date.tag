<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="date" required="true" type="java.util.Date" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate value="${date}" pattern="dd.MM.yyyy"/>