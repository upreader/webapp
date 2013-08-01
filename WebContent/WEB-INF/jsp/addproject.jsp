<jsp:useBean id="upreader" class="com.upreader.UpreaderApplication" scope="page"/>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ResourceBundle" %>

<%
    //Retreive the resourceBundle for the current language according to the user's locale.
    ResourceBundle upreaderResources = upreader.getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", request.getLocale());
%>
<!doctype html>
<html lang="en">
<head>
    <title><%=upreaderResources.getString("projectsPageTitle")%>
    </title>
    <!-- Styles-->
    <!-- Main Upreader styles -->
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css" media="screen"/>

    <!--Scripts-->
    <!-- JQuery -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>
    <!-- Angular JS MVC -->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
</head>

<body>
<div id="wrapper">
    <%@ include file="inc/topbar.jspf" %>

    <main id="content" role="main">
        <%@ include file="inc/addPrjWiz/step2.jspf" %>
    </main>

    <%@ include file="inc/footer.jspf" %>
</div>
</body>

</html>