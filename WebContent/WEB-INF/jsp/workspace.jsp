<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader - My Workspace</title>
    <jsp:include page="inc/head.jspf" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/workspace.css"  media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css"  media="screen"/>
</head>
<body>
<jsp:include page="inc/header.jspf" />

<div class="page page-workspace">
    <div class="page-header-wrapper">
        <div class="page-header">
            <div class="user-profile">
                <div class="user-profile-photo-wrapper">
                    <a class="user-profile-photo" href="#">
                        <img class="user-profile-photo-img"
                             src="http://lorempixum.com/q/260/320/people/game%20of%20chairs"/>
                    </a>
                </div>
                <div class="user-profile-details-wrapper">
                    <div class="user-profile-details">
                        <a class="user-profile-name" href="#">John Doe</a>
                        <a class="user-profile-rating" href="#">Rating: 1</a>
                        <a class="user-profile-profile" href="#">Profile: 60%</a>

                        <div class="user-profile-profile-bar">
                            ------------
                        </div>
                    </div>
                </div>
            </div>
            <div class="user-projects">
                <img src="${pageContext.request.contextPath}/images/viewproject-header-flipper.jpg">
            </div>
            <div class="user-actions">
                <img src="${pageContext.request.contextPath}/images/viewproject-header-other.jpg">
            </div>
        </div>
    </div>

    <div class="notification-wrapper">

    </div>

    <div class="mboard-wrapper">

    </div>

    <div class="financial-wrapper">

    </div>

    <div class="recommended-wrapper">

    </div>
</div>

<jsp:include page="inc/footer.jspf" />
</body>
</html>