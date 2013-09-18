<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<jsp:useBean id="upreader" class="com.upreader.UpreaderApplication" scope="session"/>
<jsp:useBean id="upreaderConstants" class="com.upreader.UpreaderConstants" scope="session"/>
<jsp:useBean id="profileData" type="com.upreader.beans.ProfileBean" class="com.upreader.beans.ProfileBean" scope="page">
    <jsp:setProperty name="profileData" property="request" value="${pageContext.request}" />
</jsp:useBean>
<%@ page import="java.util.ResourceBundle" %>
<%
    //Retreive the resourceBundle for the current language according to the user's locale.
    ResourceBundle upreaderResources = upreader.getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", request.getLocale());
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html id="ng-app" ng-app="upreaderProfileApp">
<head>
    <title>Upreader - Profile</title>
    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <c:redirect url="login"/>
        </c:when>
    </c:choose>
    <jsp:include page="inc/head.jspf"/>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css" media="screen"/>

    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/angularmodules/ngyn-select-key.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui-bootstrap-tpls-0.5.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/upreaderProfile.js"></script>
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-profile" id="page-profile" ng-controller="upreaderProfileController">
    <input type="hidden" id="user-profile-data" value='${profileData.userPublicDataJson(sessionScope.user)}' />
    <div class="profile-container">
        <div class="info1 clearfix">
            <div class="info1-col1">
                <div class="info1-title">
                    <h1><%=upreaderResources.getString("profile.title")%></h1>
                </div>
                <div class="profile-photo">
                    <img src="http://lorempixum.com/q/100/130/people/game%20of%20chairs"/>
                </div>
                <div class="profile-progress">
                    <div class="profile-progress-text">Profile 60%</div>
                    <div class="profile-progress-bar">
                        <img src="${pageContext.request.contextPath}/images/profile-progress-bar.jpg"/>
                    </div>
                </div>
                <div class="profile-actions">
                    <a ng-href="#" ng-click="isEditMode = !isEditMode" >
                        <span ng-show="isEditMode"><%=upreaderResources.getString("upreader.viewmode")%></span>
                        <span ng-show="!isEditMode"><%=upreaderResources.getString("upreader.editmode")%></span>
                    </a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a ng-href="#" ng-click="updateUserProfile()" ng-show="isEditMode"><%=upreaderResources.getString("upreader.save")%></a>
                </div>
            </div>
            <div class="info1-col2">
                <table class="info1-table">
                    <tr>
                        <td class="info1-form-label"><%=upreaderResources.getString("profile.firstName")%></td>
                        <td class="info1-form-value">
                            <p ng-bind="userData.firstName" ng-show="!isEditMode"></p>
                            <input ng-model="userData.firstName" ng-show="isEditMode"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="info1-form-label"><%=upreaderResources.getString("profile.lastName")%></td>
                        <td class="info1-form-value">
                            <p ng-bind="userData.lastName" ng-show="!isEditMode"></p>
                            <input ng-model="userData.lastName" ng-show="isEditMode"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="info1-form-label"><%=upreaderResources.getString("profile.country")%></td>
                        <td class="info1-form-value">
                            <p ng-bind="userData.country" ng-show="!isEditMode"></p>
                            <input ng-model="userData.country" ng-show="isEditMode"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="info1-form-label"><%=upreaderResources.getString("profile.state")%></td>
                        <td class="info1-form-value">
                            <p ng-bind="userData.state" ng-show="!isEditMode"></p>
                            <input ng-model="userData.state" ng-show="isEditMode"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="info1-form-label"><%=upreaderResources.getString("profile.zip")%></td>
                        <td class="info1-form-value">
                            <p ng-bind="userData.zip" ng-show="!isEditMode"></p>
                            <input ng-model="userData.zip" ng-show="isEditMode"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="info1-form-label"><%=upreaderResources.getString("profile.birthday")%></td>
                        <td class="info1-form-value">
                            <p ng-bind="userData.birthday" ng-show="!isEditMode"></p>
                            <input ng-model="userData.birthday" ng-show="isEditMode"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="info1-form-label"><%=upreaderResources.getString("profile.occupation")%></td>
                        <td class="info1-form-value">
                            <p ng-bind="userData.occupation" ng-show="!isEditMode"></p>
                            <input ng-model="userData.occupation" ng-show="isEditMode"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="info1-col3">
                <div class="info1-col3-title">
                    <h2><%=upreaderResources.getString("profile.educationTitle")%></h2>
                </div>
                <div class="education-table-wrapper">
                    <p ng-bind="userData.education" ng-show="!isEditMode"></p>
                    <input ng-model="userData.education" ng-show="isEditMode"/>
                </div>
            </div>
        </div>
        <hr/>
        <div class="info2 clearfix">
            <div class="info2-col1">
                <div class="experience-title"><%=upreaderResources.getString("profile.experienceTitle")%></div>
                <div class="experience-table-wrapper">
                    <p ng-bind="userData.experience" ng-show="!isEditMode"></p>
                    <input ng-model="userData.experience" ng-show="isEditMode"/>
                </div>
            </div>
            <div class="info2-col2">
                <div class="motivation-wrapper">
                    <table class="motivation-table">
                        <tr>
                            <td class="motivation-title"><%=upreaderResources.getString("profile.motivationTitle")%></td>
                            <td class="motivation-input">
                                <div class="motivation-display">
                                    <p ng-bind="userData.motivation" ng-show="!isEditMode"></p>
                                    <input ng-model="userData.motivation" ng-show="isEditMode"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="inspiration-wrapper">
                    <table class="inspiration-table">
                        <tr>
                            <td class="inspiration-title"><%=upreaderResources.getString("profile.inspirationTitle")%>  </td>
                            <td class="inspiration-input">
                                <p ng-bind="userData.inspiration" ng-show="!isEditMode"></p>
                                <input ng-model="userData.inspiration" ng-show="isEditMode"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <hr/>
        <div class="bio">
            <div class="bio-title">
                <h1><%=upreaderResources.getString("profile.bioTitle")%></h1>
            </div>
            <div class="bio-display">
                <p ng-bind="userData.bio" ng-show="!isEditMode"></p>
                <input ng-model="userData.bio" ng-show="isEditMode"/>
            </div>
        </div>
        <hr/>
    </div>
</div>

<jsp:include page="inc/footer.jspf"/>

</body>
</html>