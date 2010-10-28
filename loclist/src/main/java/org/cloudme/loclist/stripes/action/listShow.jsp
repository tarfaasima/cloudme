<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="script">
    function checkin(position) {
        latitude = position.coords.latitude; 
        longitude = position.coords.longitude;
        url = "/action/checkin/${actionBean.itemList.id}/" + latitude + "/" + longitude;
        $.get(url, function(data) {
            $("#items").html(data);
        });
    }
    
    function handleError(error) {
        alert(error);
    }
    
    $(document).ready(function() {
        $("a.confirm").confirm();
        navigator.geolocation.getCurrentPosition(checkin, handleError);
    });
  </s:layout-component>
  <s:layout-component name="content">
    <div>
      <a href="/action/list/index">Lists</a>
    </div>
    <div>
      ${actionBean.itemList.name}
    </div>
    <div id="items">
    </div>
    <div>
      <a href="/action/item/${actionBean.itemList.id}">Add items</a>
    </div>
    <div>
      <a href="/action/list/delete/${actionBean.itemList.id}" class="confirm" title="Do you want to delete list '${actionBean.itemList.name}'?">delete</a>
    </div>
  </s:layout-component>
</s:layout-render>