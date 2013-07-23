<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Upreader</title>
<jsp:include page="inc/head.jspf" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css"  media="screen"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css"  media="screen"/>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jquery.leanModal.min.js"></script>
</head>
<body>
<jsp:include page="inc/header.jspf" />

<div id="banner">
	<div id="banner-welcome">
		<h1 style="margin-top:200px; font-size:30px;">Welcome to Upreader</h1>
	</div>
	<c:choose>
	<c:when test="${empty pageContext.request.userPrincipal}">
	<div id="loginbar">
		<a class="button magenta flat" href="#">Create account</a>
		<a id="login" class="button orange flat" href="${pageContext.request.contextPath}/p/login">Login</a>
	</div>
	</c:when>
	<c:otherwise>
	<div id="loginbar">
		<a class="button pink flat" href="#">My Workspace</a>
	</div>
	</c:otherwise>
	</c:choose>
</div>
<table width="50%" align="center">
	<tr>
        <td><a href="<c:url value='/p/projects'/>">Projects Page</a></td>
		<td><a href="<c:url value='/p/newproject1'/>">Create Project</a></td>
		<td><a href="<c:url value='/p/myprojects'/>">My Projects</a></td>
		<td><a href="<c:url value='/p/allprojects'/>">All Projects</a></td>
	</tr>
</table>

<jsp:include page="inc/footer.jspf" />
</body>
</html>