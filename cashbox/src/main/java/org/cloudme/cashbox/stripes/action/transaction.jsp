<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="script">
    $(function() {
      $('a.close').click(function() {
        
      });
      $('tr.input td').hide();
      $('select.account').each(function() {
        $(this).load("/action/account/list");
      });
      $('tr.entry a').click(function(e) {
        e.preventDefault();
        var inputClass = 'div.' + $(this).attr('class');
        $('tr.input td').hide();
        $(inputClass).show();
        $(inputClass).load($(this).attr('href'));
        $(inputClass + ' form').submit(function(e) {
          e.preventDefault();
          $(inputClass).hide();
          var url = $(this).attr('action') + '?';
          $(this).find('input').each(function(index, input) {
            var value = encodeURI($(input).attr('value'));
            var name = $(input).attr('name');
            url = url + name + '=' + value + '&';
          });
          load(url);
          $('select.account').each(function() {
            $(this).load("/action/account/list");
          });
        });
      });
    });
  </s:layout-component>
  <s:layout-component name="body">
    <a href="/action/account">Back</a>
    <h1>${actionBean.account.name}</h1>
    <s:form beanclass="org.cloudme.cashbox.stripes.action.TransactionActionBean">
      <s:hidden name="accountId" value="${actionBean.account.id}"/>
      <s:file name="transactionFile"/>
      <s:submit name="upload" value="Upload transactions"/>
    </s:form>
    <s:form beanclass="org.cloudme.cashbox.stripes.action.TransactionActionBean">
      <s:hidden name="accountId" value="${actionBean.account.id}"/>
      <table>
        <c:set value="${actionBean.transactionImport}" var="transactionImport"/>
        <tr>
          <th>#</th>
          <th>Account</th>
          <c:forEach items="${transactionImport.mappings}" var="mapping" varStatus="col">
            <th>
              <s:select value="${mapping}" name="mapping[col.index]">
                <s:option value=""></s:option>
                <s:option value="date">Date</s:option>
                <s:option value="description">Description</s:option>
                <s:option value="value">Value</s:option>
              </s:select>
            </th>
          </c:forEach>
        </tr>
        <c:forEach items="${transactionImport.entries}" var="entry" varStatus="row">
          <tr class="entry">
            <td>${row.index}</td>
            <td>
              <s:select name="accountId[${row.index}]" class="account">
                <s:option value="-1"></s:option>
              </s:select>
              <a href="/action/account/input" class="input_${row.index}">Create account</a>
            </td>
            <c:forEach items="${entry}" var="item" varStatus="col">
              <td>${item}</td>
            </c:forEach>
          </tr>
          <tr class="input">
            <td colspan="${2 + transactionImport.width}">
              <div>
                <a href="#" class="close">close</a>
              </div>
              <div class="input_${row.index}"></div>
            </td>
          </tr>
        </c:forEach>
      </table>
    </s:form>
  </s:layout-component>
</s:layout-render>
