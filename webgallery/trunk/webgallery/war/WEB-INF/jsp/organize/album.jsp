<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:layout-render name="/WEB-INF/layout/organize.jsp" title="Albums">

<s:layout-component name="selected">albums</s:layout-component>

<s:layout-component name="content">
<s:form beanclass="org.cloudme.webgallery.stripes.action.organize.AlbumActionBean">
  <div>
    <b>Create new album:</b> <s:text name="items[${f:length(actionBean.items)}].name"/>
  </div>
  <table>
    <thead>
      <tr>
        <th>Name</th>
        <th>Description</th>
        <th></th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${actionBean.items}" var="item" varStatus="loop">
      <tr>
        <td><s:text name="items[${loop.index}].name"/></td>
        <td><s:textarea name="items[${loop.index}].description"/></td>
        <td><s:link href="/organize/photo/${item.id}">Photos (${item.photoCount})</s:link></td>
        <td><s:link href="/organize/album/delete/${item.id}">Delete</s:link></td>
      </tr>
      <s:hidden name="items[${loop.index}].id"/>
      <s:hidden name="items[${loop.index}].photoCount"/>
      </c:forEach>
    </tbody>
  </table>
  <div>
    <s:submit name="save" value="Save"/>
  </div>
</s:form>
</s:layout-component>

</s:layout-render>
