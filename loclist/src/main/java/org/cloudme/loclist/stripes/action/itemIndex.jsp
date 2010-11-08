<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="script">
(function($) {
    var changeId = null;
    
    function submit(e, async) {
            itemId = $(e).attr("name");
            if (changeId == itemId) {
                attribute = escape($(e).val());
                url = $(e).parent().attr("action") + attribute;
                alert(url);
                if (async) {
                    $.get(url);
                }
                else {
                    location.href = url;
                }
                changeId = null;
            }
    }
    
    $.fn.dynatype = function(async) {
        $(this).change(function() {
            changeId = $(this).attr("name");
        });
        $(this).blur(function() {
            alert("blur");
            submit($(this), async);
        });
        $(this).keypress(function(e) {
            alert("keypress " + e.which);
            if (e.which == 13) {
                submit($(this), async);
                return false;
            }
  <% /*
*/ %>
        });
    };
})(jQuery);
    $(document).ready(function() {
        alert("ready");
        $("input.update").dynatype(true);
        $("input.add").dynatype(false);
        $("a.delete").confirm();
    });
  </s:layout-component>
  <s:layout-component name="content">
    <div>
      <a href="/action/list/show/${actionBean.itemListId}">Back</a>
    </div>
    <div id="itemInstances">
    <c:forEach items="${actionBean.itemInstances}" var="itemInstance">
      <div>
        <form action="/action/item/${actionBean.itemListId}/update/${itemInstance.itemId}/">
          <input type="text" name="${itemInstance.itemId}" value="${itemInstance.attribute}" class="update" size="5"/>
          <a href="/action/item/${actionBean.itemListId}/remove/${itemInstance.id}" class="remove">${itemInstance.text}</a>
        </form>
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
        <form action="/action/item/${actionBean.itemListId}/add/${item.id}/">
          <input type="text" name="${item.id}" class="add" size="5"/>
          <a href="/action/item/${actionBean.itemListId}/add/${item.id}" class="add">${item.text}</a>
          <a href="/action/item/${actionBean.itemListId}/delete/${item.id}" class="delete" title="Do you want to delete item '${item.text}'?">delete</a>
        </form>
      </div>
    </c:forEach>
    </div>
  </s:layout-component>
</s:layout-render>