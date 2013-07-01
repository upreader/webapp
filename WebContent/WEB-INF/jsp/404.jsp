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

<div style="position:absolute; width:250px; height:100px; z-index:15; top:50%; left:50%; margin:-150px 0 0 -150px; text-align: center;">
<h1>Page not found</h1>
Click to go to Upreader <a href="<c:url value='/p/home'/>">Home</a>
</div>
</body>
</html>