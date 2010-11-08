<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="script">
(function($) {
    var changeId = null;
    
    $.fn.dynatype = function(async) {
        $(this).blur(function() {
            itemId = $(this).attr("name");
            if (changeId == itemId) {
                attribute = escape($(this).val());
                url = $(e).parent().attr("action") + attribute;
                if (async) {
                    $.get(url);
                }
                else {
                    location.href = url;
                }
                changeId = null;
            }
        });
        $(this).keyup(function(e) {
            if (e.which == 13) {
                $(this).blur();
                return false;
            }
            changeId = $(this).attr("name");
        });
    };
})(jQuery);
    $(document).ready(function() {
        $("input.update").dynatype(true);
        $("input.add").dynatype(false);
        $("a.delete").confirm();
    });
  </s:layout-component>
  <s:layout-component name="content">
    <div>
      <a href="/action/list/show/${actionBean.itemListId}">Back</a>
    </div>
    <div>
      <s:form beanclass="org.cloudme.loclist.stripes.action.ItemActionBean">
        <s:hidden name="itemListId" value="${actionBean.itemListId}" />
        <s:text name="attribute" value="${actionBean.attribute}" size="5"/>
        <s:text name="item.text" value="${actionBean.item.text}" class="focus" />
        <s:submit name="create" value="Add" />
      </s:form>
    </div>
    <div>
    <c:forEach items="${actionBean.listItems}" var="listItem">
      <div class="${listItem.inList ? 'inList' : 'notInList'}">
        <form action="/action/item/${actionBean.itemListId}/add/${listItem.id}/">
          <input type="text" name="${listItem.id}" class="add" size="5" value="${listItem.attribute}"/>
          <a href="/action/item/${actionBean.itemListId}/add/${listItem.id}" class="add">${listItem.text}</a>
          <a href="/action/item/${actionBean.itemListId}/delete/${listItem.id}" class="delete" title="Do you want to delete item '${listItem.text}'?">delete</a>
        </form>
      </div>
    </c:forEach>
    </div>
  </s:layout-component>
</s:layout-render>