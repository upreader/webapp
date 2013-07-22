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

<table width="50%" align="center">
	<tr>
        <td><a href="<c:url value='/p/projects'/>">Projects Page</a></td>
		<td><a href="<c:url value='/p/newproject1'/>">Create Project</a></td>
		<td><a href="<c:url value='/p/myprojects'/>">My Projects</a></td>
		<td><a href="<c:url value='/p/allprojects'/>">All Projects</a></td>
	</tr>
</table>

</body>
</html>