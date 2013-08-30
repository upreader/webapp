<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader - My Workspace</title>
    <jsp:include page="inc/head.jspf"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/workspace.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css" media="screen"/>

    <!-- Monitor Board -->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/dataTables.scroller.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/mboard.js"></script>
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.dataTables.css"
          media="screen"/>
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dataTables.scroller.css"
          media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mboard.css" media="screen"/>
    <!-- Monitor Board -->
</head>
<body>
<jsp:include page="inc/header.jspf"/>

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
    </div>
    <!-- Monitor Board -->
</div>

<div class="financial-wrapper">

</div>

<div class="recommended-wrapper">

</div>
</div>

<jsp:include page="inc/footer.jspf"/>
</body>
</html>