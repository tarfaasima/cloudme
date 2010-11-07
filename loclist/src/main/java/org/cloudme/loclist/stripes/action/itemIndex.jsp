<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="script">
    var changeId = null;
    
    $(document).ready(function() {
        $("a.delete").confirm();
        $("input.update").change(function() {
            changeId = $(this).attr("name");
        });
        $("input.update").blur(function() {
            itemId = $(this).attr("name");
            if (changeId == itemId) {
                attribute = escape($(this).val());
                $.get("/action/item/${actionBean.itemListId}/update/" + itemId + "/" + attribute);
                changeId = null;
            }
        });
        $("input.add").change(function() {
            changeId = $(this).attr("name");
        });
        $("input.add").blur(function() {
            itemId = $(this).attr("name");
            if (changeId == itemId) {
                attribute = escape($(this).val());
                location.href = "/action/item/${actionBean.itemListId}/add/" + itemId + "/" + attribute;
                changeId = null;
            }
        });
    });
  </s:layout-component>
  <s:layout-component name="content">
    <div>
      <a href="/action/list/show/${actionBean.itemListId}">Back</a>
    </div>
    <div id="itemInstances">
    <c:forEach items="${actionBean.itemInstances}" var="itemInstance">
      <div>
        <input type="text" name="${itemInstance.itemId}" value="${itemInstance.attribute}" class="update" size="5"/>
        <a href="/action/item/${actionBean.itemListId}/remove/${itemInstance.id}" class="remove">
          ${itemInstance.text}
        </a>
      </div>
    </c:forEach>
    </div>
    <div>
      <s:form beanclass="org.cloudme.loclist.stripes.action.ItemActionBean">
        <s:hidden name="itemListId" value="${actionBean.itemListId}" />
        <s:text name="attribute" value="${actionBean.attribute}" size="5"/>
        <s:text name="item.text" value="${actionBean.item.text}" class="focus" />
        <s:submit name="create" value="Add" />
      </s:form>
    </div>
    <div id="items">
    <c:forEach items="${actionBean.items}" var="item">
      <div>
        <input type="text" name="${item.id}" class="add" size="5"/>
        <a href="/action/item/${actionBean.itemListId}/add/${item.id}" class="add">${item.text}</a>
        <a href="/action/item/${actionBean.itemListId}/delete/${item.id}" class="delete" title="Do you want to delete item '${item.text}'?">delete</a>
      </div>
    </c:forEach>
    </div>
  </s:layout-component>
</s:layout-render>