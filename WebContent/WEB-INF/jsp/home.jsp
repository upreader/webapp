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

<div class="page">
    <div class="home-container">
        <div class="home-content">
            <div class="banner-welcome">
                <h1>Welcome to Upreader</h1>
            </div>
            <c:choose>
                <c:when test="${not empty pageContext.request.userPrincipal}">
                    <div class="home-buttons">
                        <a class="button pink flat" href="#">My Workspace</a>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="inc/footer.jspf" />

</body>
</html>