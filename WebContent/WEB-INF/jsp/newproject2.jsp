<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Upreader</title>
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/upreader.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>
</head>
<body>
<%@ include file="inc/topbar.jspf" %>

<h1>Create a new project - Step 2</h1>

<form id="addform" action="${pageContext.request.contextPath}/i/s/p" method="post">
<table width="80%">
<tr><td width="20%" align="right">Book price:</td><td align="left"><input type="text" name="bookprice"/>$</td></tr>
<tr><td width="20%" align="right">Publishing years:</td><td align="left"><input type="text" name="pubyears"/></td></tr>
<tr><td width="20%" align="right">Percent to sale:</td><td align="left"><input type="text" name="percentsale"/></td></tr>
<tr><td align="right">&nbsp;</td><td>&nbsp;</td></tr>
<tr><td>&nbsp;</td><td align="left"><input id="btnSubmit" type="submit" value="Finish"/></td></tr>
</table>
<input type="hidden" name="do" value="s2" />
</form>

</body>
</html>
