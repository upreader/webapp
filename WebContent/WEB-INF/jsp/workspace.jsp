<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<jsp:useBean id="upreader" class="com.upreader.UpreaderApplication" scope="session"/>
<jsp:useBean id="upreaderConstants" class="com.upreader.UpreaderConstants" scope="session" />
<jsp:useBean id="workspaceData" type="com.upreader.beans.WorkspaceBean" class="com.upreader.beans.WorkspaceBean" scope="page">
</jsp:useBean>
<%@ page import="java.util.ResourceBundle" %>
<%
    //Retreive the resourceBundle for the current language according to the user's locale.
    ResourceBundle upreaderResources = upreader.getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", request.getLocale());
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" id="ng-app" ng-app="upreaderWorkspaceApp">
<head>
    <title><%=upreaderResources.getString("workspace.title")%></title>
    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <c:redirect url="login"/>
        </c:when>
    </c:choose>
    <jsp:include page="inc/head.jspf"/>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/workspace.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css" media="screen"/>

    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/angularmodules/ngyn-select-key.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui-bootstrap-tpls-0.5.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/upreaderWorkspace.js"></script>

    <!-- Monitor Board -->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/dataTables.scroller.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/mboard.js"></script>

    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.dataTables.css" media="screen"/>
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dataTables.scroller.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mboard.css" media="screen"/>
    <!-- Monitor Board -->
</head>
<body>
<jsp:include page="inc/header.jspf"/>

<div class="page page-workspace" id="page-workspace"  ng-controller="upreaderWorkspaceController">
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
                        <a class="user-profile-name" href="#">${sessionScope.user.firstName} ${sessionScope.user.lastName}</a>
                        <a class="user-profile-rating" href="#"><%=upreaderResources.getString("upreader.rating")%> ${sessionScope.user.rating}</a>
                        <a class="user-profile-profile" href="#"><%=upreaderResources.getString("upreader.profile")%> 60%</a>

                        <input type="hidden" id="USER_ID" value="${sessionScope.user.id}" />
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
    <div class="page-content">
        <hr/>

        <div class="inbox-wrapper">
            <accordion close-others="notificationsOpenOneAtATime" ng-repeat="(idx, notificationGroup) in userNotifications">
                <accordion-group heading="{{notificationGroup.group}}" >
                    <table class="inbox-table">
                        <thead>
                        <tr>
                            <th width="80%">SUBJECT</th>
                            <th>Date</th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr ng-repeat="notification in notificationGroup.notifications">
                                <td>
                                    <accordion>
                                        <accordion-group heading="{{notification.subject}}">
                                            {{notification.message}}
                                        </accordion-group>
                                    </accordion>
                                </td>
                                <td colspan="2">{{notification.date | date:<%=upreaderResources.getString("projectsTable.dateFormat")%>}}</td>
                            </tr>
                        </tbody>
                    </table>
                </accordion-group>
            </accordion>
        </div>

        <hr/>

        <div class="mboard-wrapper">
            <div class="mboard-title">
                <h1>Monitor Board</h1>
            </div>
            <!-- Monitor Board -->
            <div class="mboard">
                <table class="mboard-table" id="item_table" cellspacing="0">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Project</th>
                        <th>Author</th>
                        <th>Book Price #</th>
                        <th>Upreaders #</th>
                        <th>Share Value $</th>
                        <th>IRS Progress</th>
                        <th>Days to DL</th>
                        <th>Opened #</th>
                        <th>Sold Books #</th>
                        <th>Shares for sale #</th>
                        <th>Subscribers #</th>
                        <th>Subscr. Price $</th>
                        <th>Income $</th>
                        <th>Derivatives</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="odd">
                        <td colspan="9" class="dataTables_empty">Loading...</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- Monitor Board -->
            <div class="pin-projects">
                <div class="pin-projects-text">lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</div>
                <div class="pin-projects-button"><input id="pin-button" type="button" class="button-gray" value="Pin more projects"/></div>
            </div>
        </div>

        <hr/>

        <div class="financial-wrapper">
            <div class="financial-title">
                <h1>Financial situation</h1>
            </div>
        </div>

        <hr/>

        <div class="recommended-wrapper">
            <div class="recommended-title">
                <h1>Recommended for you</h1>
            </div>
        </div>
    </div>
</div>

</div>

<jsp:include page="inc/footer.jspf"/>
</body>
</html>