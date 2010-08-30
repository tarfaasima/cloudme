<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>

<s:form beanclass="de.moritzpetersen.homepage.stripes.action.admin.ConfigActionBean">
  <div>Data load token</div>
  <div><s:text name="config.dataLoadToken" /></div>
  <div><s:submit name="save" value="Save" /></div>
</s:form>