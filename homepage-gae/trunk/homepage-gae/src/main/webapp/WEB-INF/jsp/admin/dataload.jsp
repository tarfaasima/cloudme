<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>

<s:errors/>
<s:messages/>
<s:form beanclass="de.moritzpetersen.homepage.stripes.action.admin.DataLoadActionBean">
  <div><s:textarea name="data" /></div>
  <div><s:submit name="save" value="Save" /></div>
</s:form>