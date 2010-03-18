<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:layout-render name="/WEB-INF/layout/organize.jsp" title=" - Settings">

<s:layout-component name="content">
<s:form beanclass="org.cloudme.webgallery.stripes.action.organize.SettingsActionBean">
  <s:hidden name="metaData.id" />
  <div>Flickr</div>
  <table>
    <thead>
      <tr>
        <th>Key</th>
        <th>Secret</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td><s:text name="metaData.key" /></td>
        <td><s:text name="metaData.secret" /></td>
        <td><a href="${actionBean.flickrLoginLink}">Authenticate</a></td>
      </tr>
    </tbody>
  </table>
  <div>
    <s:submit value="Save" name="save"/>
  </div>
</s:form>
</s:layout-component>

</s:layout-render>
