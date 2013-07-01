<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Login</title>
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/upreader.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>
</head>
<body>
<%@ include file="inc/topbar.jspf" %>

<div style="position:absolute; width:250px; height:100px; z-index:15; top:50%; left:50%; margin:-150px 0 0 -150px;">
	<FORM action="j_security_check" method="post">
		<table width="100%">
			<tr>
				<td align="right">Username:</td>
				<td align="left"><INPUT type="text" name="j_username" /></td>
			</tr>
			<tr>
				<td align="right">Password:</td>
				<td align="left"><INPUT type="password" name="j_password" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><INPUT type="submit" value="Login" /></td>
			</tr>
		</table>
		<input type="hidden" name="j_uri" value="loginSuccessful" />
	</FORM>
</div>
</body>
</html>