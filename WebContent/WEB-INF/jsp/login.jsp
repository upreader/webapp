<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Upreader</title>
<jsp:include page="inc/head.jspf" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css"  media="screen"/>
</head>
<body>
<jsp:include page="inc/header.jspf" />

<div style="background-color:white">
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
		<input type="hidden" name="j_uri" value="/i/loginSuccessful" />
	</FORM>
</div>
<c:if test="${not empty param.error}">
<p>Error logging in</p>
</c:if>

<jsp:include page="inc/footer.jspf" />
</body>
</html>
