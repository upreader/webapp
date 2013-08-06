<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css"  media="screen"/>
</head>
<body>

<jsp:include page="inc/header.jspf" />

<div class="page page-home">
    <div class="home-container">
        <div class="home-content">
            <div class="banner-welcome">
                <h1>You have confirmed your Upreader account.</h1>
                <p>You can now <a href="<c:url value='/p/login'/>">login</a> to your account </p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="inc/footer.jspf" />

</body>
</html>