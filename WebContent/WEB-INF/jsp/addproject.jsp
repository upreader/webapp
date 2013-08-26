<?xml version="1.0" ?>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<jsp:useBean id="upreader" class="com.upreader.UpreaderApplication" scope="session"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.upreader.UpreaderConstants" %>
<%@ page import="java.util.ResourceBundle" %>

<%
    //Retreive the resourceBundle for the current language according to the user's locale.
    ResourceBundle upreaderResources = upreader.getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", request.getLocale());
%>
<!doctype html>
<html lang="en" id="ng-app" ng-app="upreaderAddPrjApp">
<head>
    <title><%=upreaderResources.getString("addProjectWizard.title")%>
    </title>
    <!-- Styles-->
    <!-- Main Upreader styles -->
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css" media="screen"/>
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addprojectwizard.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/projectcontent.css" media="screen"/>
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tagging.css" media="screen"/>
    <link href="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/css/dropzone.css" type="text/css" rel="stylesheet" />

    <!--Scripts-->
    <!-- JQuery -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>

    <!-- Angular JS MVC -->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui-bootstrap-tpls-0.5.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/tinymce.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/angular-ui-tinymce/tinymce.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinymce/plugins/filemanager/js/dropzone.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/addProjectWizard.js"></script>
</head>

<body>

    <%@ include file="inc/header.jspf" %>

    <div class="page" id="addProjectWizardController" ng-controller="addProjectWizardController">
            <div class="page-addproject-wizard" role="main">
                <c:choose>
                    <c:when test="${empty sessionScope.wizardData.currentStep}">
                        <%@ include file="inc/addPrjWiz/step1.jspf" %>
                    </c:when>
                    <c:when test="${not empty sessionScope.wizardData.currentStep && sessionScope.wizardData.currentStep eq '1'}">
                        <%@ include file="inc/addPrjWiz/step1.jspf" %>
                    </c:when>
                    <c:when test="${not empty sessionScope.wizardData.currentStep && sessionScope.wizardData.currentStep eq '2'}">
                        <%@ include file="inc/addPrjWiz/step2.jspf" %>
                    </c:when>
                    <c:when test="${not empty sessionScope.wizardData.currentStep && sessionScope.wizardData.currentStep eq '3'}">
                        <%@ include file="inc/addPrjWiz/step3.jspf" %>
                    </c:when>
                    <c:when test="${not empty sessionScope.wizardData.currentStep && sessionScope.wizardData.currentStep eq '4'}">
                        <%@ include file="inc/addPrjWiz/step4.jspf" %>
                    </c:when>
                    <c:when test="${not empty sessionScope.wizardData.currentStep && sessionScope.wizardData.currentStep eq '5'}">
                        <%@ include file="inc/addPrjWiz/step5.jspf" %>
                    </c:when>
                    <c:when test="${not empty sessionScope.wizardData.currentStep && sessionScope.wizardData.currentStep eq '6'}">
                        <%@ include file="inc/addPrjWiz/step6.jspf" %>
                    </c:when>
                </c:choose>
            </div>
    </div>

    <%@ include file="inc/footer.jspf" %>

</body>

</html>