<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="script">
    function checkin(position) {
        alert("checkin");
        latitude = position.coords.latitude; 
        longitude = position.coords.longitude;
        url = "/action/checkin/${actionBean.itemList.id}/" + latitude + "/" + longitude;
        alert(url);
        $.get(url), function(data) {
            alert(data);
            $(".items").html(data);
        }
    }
    
    $(document).ready(function() {
      alert("ready");
      navigator.geolocation.getCurrentPosition(checkin);
      alert("done");
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
  </s:layout-component>
</s:layout-render>