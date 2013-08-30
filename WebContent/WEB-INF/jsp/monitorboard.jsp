<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" media="screen"/>

    <!-- Monitor Board -->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/dataTables.scroller.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/mboard.js"></script>
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.dataTables.css"media="screen"/>
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dataTables.scroller.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mboard.css" media="screen"/>
    <!-- Monitor Board -->
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-home">
    <div class="home-container">
        <div class="home-content">
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
</div>

<jsp:include page="inc/footer.jspf"/>
</body>
</html>