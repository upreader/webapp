<?xml version="1.0" encoding="utf-8" ?>
<%@tag description="Admin Page template" isELIgnored="false"%>
<%@attribute name="content" fragment="true"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Upreader Administration</title>
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/upreader.css" media="screen" />
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datatable.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.raty.min.js"></script>
</head>
<body>
	<%@ include file="../inc/topbar.jspf"%>
	<h1>Upreader Administration</h1>

	<div class="colmask leftmenu">
		<div class="colleft">
			<div class="col1">
				<jsp:doBody />
			</div>
			<div class="col2">
				<%@ include file="../admin/nav.jspf"%>
			</div>
		</div>
	</div>

	<div id="footer">Copyright (c) Upreader Inc. 2013</div>
</body>
</html>