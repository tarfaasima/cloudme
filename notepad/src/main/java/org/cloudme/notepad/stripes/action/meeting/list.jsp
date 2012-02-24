<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:default title="Topic List">
  <t:meetings groups="${actionBean.meetingGroups}"/>
</t:default>
