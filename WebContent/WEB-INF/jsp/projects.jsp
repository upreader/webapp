<jsp:useBean id="upreader" class="com.upreader.UpreaderApplication" scope="page" />
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ResourceBundle" %>


<%
    //Retreive the resourceBundle for the current language according to the user's locale.
    ResourceBundle upreaderResources = upreader.getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", request.getLocale());
%>
<!doctype html>
<html lang="en" id="ng-app" ng-app="upreaderPrjsApp">
<head>
    <title><%=upreaderResources.getString("projectsPageTitle")%></title>
    <!-- Styles-->
        <!-- Main Upreader styles -->
        <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/upreader.css" media="screen" />

    <!--Scripts-->
        <!-- JQuery -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>
        <!-- Angular JS MVC -->
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
        <!-- Upreader Projects Table Logic -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/upreaderProjectsMainTable.js"></script>

</head>
<body>
    <jsp:include page="inc/header.jspf" />

    <div class="page page-projects">

        <div id="projectsHeader">
            <div role="navigation" id="storyPreferences">
                <nav>
                    <a href="">First command</a>
                </nav>
            </div>
            <div role="banner" id="projectsPics">
                <header role="banner">
                         pics
                </header>
            </div>
        </div>

        <main id="content" role="main" ng-controller="projectsTableController">
            <%-- Eager Data Initialization for the projects table --%>
            <script type="text/javascript">
                function initProjectsTableData() {

                   //TODO If necessary to have eager init
                }
            </script>
            <%=upreaderResources.getString("projectsPage.search")%> <input ng-model="query">
            <table border="1" id="projectsTable">
                   <tr id="headerRow">
                       <th>
                           <div id="orderTitle">
                               <a href="" ng-click="predicate = 'title';  reverse=false">Sort Asc</a>
                               <a href="" ng-click="predicate = 'title';  reverse=true">Sort Desc</a>
                           </div>
                       </th>
                       <th>
                           <div id="orderCover">
                               <a href="" ng-click="predicate = 'cover';  reverse=false">Sort Asc</a>
                               <a href="" ng-click="predicate = 'cover';  reverse=true">Sort Desc</a>
                           </div>
                       </th>
                       <th>
                           <div id="orderGenre">
                               <a href="" ng-click="predicate = 'genre';  reverse=false">Sort Asc</a>
                               <a href="" ng-click="predicate = 'genre';  reverse=true">Sort Desc</a>
                           </div>
                       </th>
                       <th>Author</th>
                       <th>Rating</th>
                       <th>bookPrice</th>
                       <th>1</th>
                       <th>
                           <div id="orderIrsValue">
                               <a href="" ng-click="predicate = 'irs';  reverse=false">Sort Asc</a>
                               <a href="" ng-click="predicate = 'irs';  reverse=true">Sort Desc</a>
                           </div>
                       </th>
                       <th>
                           <div id="orderSharesToSale">
                               <a href="" ng-click="predicate = 'sharesToSale';  reverse=false">Sort Asc</a>
                               <a href="" ng-click="predicate = 'sharesToSale';  reverse=true">Sort Desc</a>
                           </div>
                       </th>
                       <th>
                           <div id="orderSharePrice">
                               <a href="" ng-click="predicate = 'sharePrice';  reverse=false">Sort Asc</a>
                               <a href="" ng-click="predicate = 'sharePrice';  reverse=true">Sort Desc</a>
                           </div>
                       </th>
                       <th>Irs Status</th>
                       <th>
                           <div id="orderDeadline">
                               <a href="" ng-click="predicate = 'deadline';  reverse=false">Sort Asc</a>
                               <a href="" ng-click="predicate = 'deadline';  reverse=true">Sort Desc</a>
                           </div>
                       </th>
                       <th>Opened</th>
                       <th>Publishers</th>
                   </tr>
                   <tr ng-repeat="project in projects | filter:query | orderBy:predicate:reverse">
                       <td ng-bind="project.title"></td>
                       <td ng-bind="project.cover"></td>
                       <td ng-bind="project.genre"></td>
                       <td ng-bind="project.author.user.email"></td>
                       <td ng-bind="project.author.user.rating"></td>
                       <td ng-bind="project.bookPrice"></td>
                       <td ng-bind="project.subscribers.length"></td>
                       <td ng-bind="project.irs"></td>
                       <td ng-bind="project.sharesToSale"></td>
                       <td ng-bind="project.sharePrice"></td>
                       <td>Irs Status</td>
                       <td ng-bind="project.deadline | date:<%=upreaderResources.getString("projectsTable.dateFormat")%>"></td>
                       <td ng-bind="project.noViews"></td>
                       <td>Publishers</td>
                       <td><input type="checkbox"/></td>
                   </tr>
            </table>
        </main>

        <div role="contentInfo" id="storyFooter">
            <div role="contentInfo" id="addStory">
                Add Story
            </div>
            <div role="contentInfo" id="stockDetails">
                Stock Details
            </div>
            <div role="contentInfo" id="storyControls">

            </div>
        </div>
        <footer role="contentinfo">

        </footer>
    </div>
    <jsp:include page="inc/footer.jspf" />
</body>
</html>