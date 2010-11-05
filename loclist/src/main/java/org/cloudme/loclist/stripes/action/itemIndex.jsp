<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="script">
    $(document).ready(function() {
        $("a.delete").confirm();
        $("a.add").click(function() {
            $(this).parent().fadeOut();
            $.get($(this).attr("href"));
            return false;
        });
    });
  </s:layout-component>
  <s:layout-component name="content">
    <div>
      <a href="/action/list/show/${actionBean.itemListId}">Back</a>
    </div>
    <c:forEach items="${actionBean.items}" var="item">
      <div>
        <a href="/action/item/${actionBean.itemListId}/add/${item.id}" class="add">${item.text}</a>
        <a href="/action/item/${actionBean.itemListId}/delete/${item.id}" class="delete" title="Do you want to delete item ${item.text}?">delete</a>
      </div>
    </c:forEach>
    <div>
      <s:form beanclass="org.cloudme.loclist.stripes.action.ItemActionBean">
        <s:hidden name="itemListId" value="${actionBean.itemListId}" />
        <s:text name="item.text" value="${actionBean.item.text}" class="focus"/>
        <s:submit name="save" value="Save" />
      </s:form>
    </div>
  </s:layout-component>
</s:layout-render>