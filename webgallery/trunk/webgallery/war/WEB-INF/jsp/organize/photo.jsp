<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title=" - Organize Photos">
<s:layout-component name="content">
<jsp:include page="/WEB-INF/layout/_menu.jsp"/>
<s:messages/>
<s:form beanclass="org.cloudme.webgallery.stripes.action.organize.PhotoActionBean">
  <s:hidden name="albumId" value="${actionBean.albumId}"/>
  <div>
    <b>Upload new photos:</b> (JPEG, PNG or ZIP file allowed) <s:file name="photoFile"/> <s:submit name="upload" value="Upload" />
  </div>
</s:form>
<s:form beanclass="org.cloudme.webgallery.stripes.action.organize.PhotoActionBean">
  <s:hidden name="albumId" value="${actionBean.albumId}"/>
  <table>
    <thead>
      <tr>
        <th rowspan="2"></th>
        <th rowspan="2">Name</th>
        <th colspan="5">Crop balance</th>
        <th colspan="2" rowspan="2"></th>
      </tr>
      <tr>
        <th>0%</th>
        <th>25%</th>
        <th>50%</th>
        <th>75%</th>
        <th>100%</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${actionBean.photos}" var="photo" varStatus="loop">
      <s:hidden name="photo[${loop.index}].id"/>
      <tr>
        <td>
          <a href="/gallery/photo/${photo.id}_l.jpg" class="lightbox"><img src="/gallery/photo/${photo.id}_t.jpg" class="photo"/></a>
        </td>
        <td>
          <s:textarea name="photos[${loop.index}].name" class="name"/>
        </td>
      <td>
        <s:radio name="photos[${loop.index}].cropBalance" value="0%" formatType="percentage"/>
      </td>
      <td>
        <s:radio name="photos[${loop.index}].cropBalance" value="25%" formatType="percentage"/>
      </td>
      <td>
        <s:radio name="photos[${loop.index}].cropBalance" value="50%" formatType="percentage"/>
      </td>
      <td>
        <s:radio name="photos[${loop.index}].cropBalance" value="75%" formatType="percentage"/>
      </td>
      <td>
        <s:radio name="photos[${loop.index}].cropBalance" value="100%" formatType="percentage"/>
      </td>
      <td>
        <s:link href="/organize/photo/${actionBean.albumId}/flickr/${photo.id}">Post to Flickr</s:link> 
      </td>
      <td>
        <s:link href="/organize/photo/${actionBean.albumId}/delete/${photo.id}">Delete</s:link>
      </td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
  <div id="save">
    <s:submit name="save" value="Save" />
  </div>
</s:form>
</s:layout-component>
<s:layout-component name="footerLink">
<s:link beanclass="org.cloudme.webgallery.stripes.action.LogoutActionBean">logout</s:link>
</s:layout-component>
</s:layout-render>
