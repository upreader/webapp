<?xml version="1.0" encoding="utf-8" ?>
<%@page import="com.upreader.controller.ProjectForm"%>
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

<h1>Create a new project - Step 1</h1>

<H4 style="color: red"><c:out value="${uerror}"/></H4>

<form id="addform" action="${pageContext.request.contextPath}/i/s/p" method="post" enctype="multipart/form-data">
<table width="80%">
<tr><td width="20%" align="right">Title:</td><td align="left"><input type="text" name="title" value="<c:out value="${title}"/>"/></td></tr>
<tr><td width="20%" align="right">Genre:</td><td align="left"><input type="text" name="genre" value="<c:out value="${genre}"/>"/></td></tr>
<tr><td width="20%" align="right">Subgenres:</td><td align="left"><input type="text" name="subgenres" value="<c:out value="${subgenres}"/>"/></td></tr>
<tr><td width="20%" align="right">Book:</td><td align="left"><input type="file" name="book" /></td></tr>
<tr><td width="20%" align="right">Sample:</td><td align="left"><input type="file" name="sample"/></td></tr>
<tr><td width="20%" align="right">Cover:</td><td align="left"><input type="file" name="cover"/></td></tr>
<tr><td width="20%" align="right">Pitch:</td><td align="left"><TEXTAREA rows="5" cols="80" name="pitch"><c:out value="${pitch}"/></TEXTAREA></td></tr>
<tr><td width="20%" align="right">Synopsis:</td><td align="left"><TEXTAREA rows="5" cols="80" name="synopsis"/><c:out value="${synopsis}"/></TEXTAREA></td></tr>
<tr><td width="20%" align="right">References:</td><td align="left"><TEXTAREA rows="5" cols="80" name="references"/><c:out value="${references}"/></TEXTAREA></td></tr>
<tr><td width="20%" align="right">Backstory:</td><td align="left"><TEXTAREA rows="5" cols="80" name="backstory"/><c:out value="${backstory}"/></TEXTAREA></td></tr>
<tr><td align="right">&nbsp;</td><td>&nbsp;</td></tr>
<tr><td>&nbsp;</td><td align="left"><input id="btnSubmit" type="submit" value="Next"/></td></tr>
</table>
<input type="hidden" name="do" value="s1" />
</form>

</body>
</html>
