<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Upreader</title>
<jsp:include page="../inc/head.jspf" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css"  media="screen"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css"  media="screen"/>
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datatable_crud.css" media="screen" />
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.raty.min.js"></script>

</head>
<body>
<jsp:include page="../inc/header.jspf" />

<div id="wrapper">
	<div id="admin-banner-welcome">
		<h1 style="margin-top:10px; font-size:30px;">Upreader Administration</h1>	
	</div>
	<div class="colmask leftmenu">
		<div class="colleft">
			<div class="col1">
				<h2>Server status</h2>
			</div>
			<div class="col2">
				<%@ include file="../admin/nav.jspf"%>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../inc/footer.jspf" />

</body>
</html>