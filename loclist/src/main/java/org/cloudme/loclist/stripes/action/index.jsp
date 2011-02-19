<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/iphone.jsp">
  <s:layout-component name="header">Loclist</s:layout-component>
  <s:layout-component name="content">
    <ul class="edgeToEdge">
      <c:forEach items="${actionBean.notes}" var="note">
        <li>
          <a href="/action/list/checkin/${note.id}/" class="checkin">
            <span>
            ${note.name}
            </span>
          </a>
        </li>
      </c:forEach>
    </ul>
    <div class="roundedRectangle">
      <div class="formLabel">
        Create a new list
      </div>
      <form action="/action/index/create">
        <div class="inputContainer">
          <input type="text" name="note.name" placeholder="Name of list" class="submit"/>
        </div>
      </form>
    </div>
  </s:layout-component>
</s:layout-render>
