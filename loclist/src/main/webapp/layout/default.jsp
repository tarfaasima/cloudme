<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-definition>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <title>Loclist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/simple-confirm-0.1.js"></script>
    <script type="text/javascript">
	  $(document).ready(function() {
	    $('.focus').focus();
	  });
	</script>
    <script type="text/javascript"><s:layout-component name="script"/></script>
  </head>
  <body>
    <s:layout-component name="content"/>
  </body>
</html>
</s:layout-definition>