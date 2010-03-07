<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title=" - Organize Photos">
<s:layout-component name="headCss">
<link rel="stylesheet" type="text/css" href="/css/jquery.lightbox-0.5.css" media="screen" />
</s:layout-component>
<s:layout-component name="headJs">
<script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/js/jquery.lightbox-0.5.js"></script>
<script type="text/javascript">
$(function() {
    $('a.lightbox').lightBox({
        imageLoading:           '/images/lightbox/lightbox-ico-loading.gif',
        imageBtnPrev:           '/images/lightbox/lightbox-btn-prev.gif',
        imageBtnNext:           '/images/lightbox/lightbox-btn-next.gif',
        imageBtnClose:          '/images/lightbox/lightbox-btn-close.gif',
        imageBlank:             '/images/lightbox/lightbox-blank.gif',
        containerResizeSpeed:   200,
        txtImage:               'Photo'
    });
    $('a#close').click(function() {
        fadeOut("slow");
    });
});
</script>
</s:layout-component>
<s:layout-component name="content">
<jsp:include page="/WEB-INF/layout/_menu.jsp"/>
<div id="photos">
<s:messages/>
<div id="upload">
<s:form beanclass="org.cloudme.webgallery.stripes.action.organize.PhotoActionBean">
  <s:hidden name="albumId" value="${actionBean.albumId}"/>
  <span>
    Upload new photos:
  </span>
  <span>
    (JPEG, PNG or ZIP file allowed)
    <s:file name="photoFile"/>
  </span>
  <s:submit name="upload" value="Upload" />
</s:form>
</div>
<s:form beanclass="org.cloudme.webgallery.stripes.action.organize.PhotoActionBean">
  <div id="save">
    <s:submit name="save" value="Save" />
  </div>
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
          <a href="/gallery/photo/${photo.id}_l.jpg" class="lightbox"><img src="/gallery/photo/${photo.id}_t.jpg"/></a>
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
</div>
</s:layout-component>
<s:layout-component name="footerLink">
<s:link beanclass="org.cloudme.webgallery.stripes.action.LogoutActionBean">logout</s:link>
</s:layout-component>
</s:layout-render>
