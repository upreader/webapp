<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Upreader</title>
<jsp:include page="inc/head.jspf" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css"  media="screen"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css"  media="screen"/>
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datatable.css" media="screen" />
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.raty.min.js"></script>

</head>
<body>
<jsp:include page="inc/header.jspf" />

<div style="height:500px; margin:0 auto; text-align:center; background: none repeat scroll 0 0 #FFFFFF;">
<h1 style="padding-top:200px">Page not found</h1>
Click to go to Upreader <a href="<c:url value='/p/home'/>">Home</a>
</div>
<jsp:include page="inc/footer.jspf" />
</body>
</html>