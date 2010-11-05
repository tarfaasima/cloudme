<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="content">
    <div>
      <a href="/action/list/index">Cancel</a>
    </div>
    <s:form beanclass="org.cloudme.loclist.stripes.action.ListActionBean">
      <s:text name="itemList.name" class="focus"></s:text>
      <s:submit name="save" value="Save"></s:submit>
    </s:form>
  </s:layout-component>
</s:layout-render>