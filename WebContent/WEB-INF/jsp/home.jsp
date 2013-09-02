<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" media="screen"/>
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

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/projectcard.css" media="screen"/>
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-home">
    <div class="home-container">
        <div class="top-banner">
            <img src="http://lorempixum.com/q/1024/300/people/game%20of%20chairs">
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
            <!-- Monitor Board -->
        </div>

        <hr/>

        <div class="fb-projects-wrapper">
            <div class="project-card">
                <div class="project-card-contents">
                    <div class="project-card-title">O carte cu un titlu prea lung pentru un singur rand</div>
                    <div class="project-card-cover">
                        <img class="user-profile-photo-img"
                             src="http://lorempixum.com/q/170/190/people/game%20of%20chairs"/>
                    </div>
                    <div class="project-card-author">Alexandru Octavian David Protopopitoricescovici</div>
                    <div class="project-card-author-rating">rating 1</div>
                    <div class="project-card-status">
                        <img src="${pageContext.request.contextPath}/images/project-card-status.jpg">
                    </div>
                    <div class="project-card-details">
                        <div>IRS: 7009$</div>
                        <div>Days to deaddivne: 13</div>
                        <div>Share price: 1$</div>
                        <div>Upreders: 433</div>
                        <div>Shares total: 7009</div>
                        <div>Shares remaining: 6000</div>
                        <div>Opened 12445 times</div>
                        <div>Publishers interested: 3</div>
                    </div>
                </div>
            </div>
            <div class="project-card">
                <div class="project-card-contents">
                    <div class="project-card-title">O carte cu un titlu prea lung pentru un singur rand</div>
                    <div class="project-card-cover">
                        <img class="user-profile-photo-img"
                             src="http://lorempixum.com/q/170/190/people/game%20of%20chairs"/>
                    </div>
                    <div class="project-card-author">Alexandru Octavian David Protopopitoricescovici</div>
                    <div class="project-card-author-rating">rating 1</div>
                    <div class="project-card-status">
                        <img src="${pageContext.request.contextPath}/images/project-card-status.jpg">
                    </div>
                    <div class="project-card-details">
                        <div>IRS: 7009$</div>
                        <div>Days to deaddivne: 13</div>
                        <div>Share price: 1$</div>
                        <div>Upreders: 433</div>
                        <div>Shares total: 7009</div>
                        <div>Shares remaining: 6000</div>
                        <div>Opened 12445 times</div>
                        <div>Publishers interested: 3</div>
                    </div>
                </div>
            </div>
            <div class="project-card">
                <div class="project-card-contents">
                    <div class="project-card-title">O carte cu un titlu prea lung pentru un singur rand</div>
                    <div class="project-card-cover">
                        <img class="user-profile-photo-img"
                             src="http://lorempixum.com/q/170/190/people/game%20of%20chairs"/>
                    </div>
                    <div class="project-card-author">Alexandru Octavian David Protopopitoricescovici</div>
                    <div class="project-card-author-rating">rating 1</div>
                    <div class="project-card-status">
                        <img src="${pageContext.request.contextPath}/images/project-card-status.jpg">
                    </div>
                    <div class="project-card-details">
                        <div>IRS: 7009$</div>
                        <div>Days to deaddivne: 13</div>
                        <div>Share price: 1$</div>
                        <div>Upreders: 433</div>
                        <div>Shares total: 7009</div>
                        <div>Shares remaining: 6000</div>
                        <div>Opened 12445 times</div>
                        <div>Publishers interested: 3</div>
                    </div>
                </div>
            </div>
            <div class="project-card">
                <div class="project-card-contents">
                    <div class="project-card-title">O carte cu un titlu prea lung pentru un singur rand</div>
                    <div class="project-card-cover">
                        <img class="user-profile-photo-img"
                             src="http://lorempixum.com/q/170/190/people/game%20of%20chairs"/>
                    </div>
                    <div class="project-card-author">Alexandru Octavian David Protopopitoricescovici</div>
                    <div class="project-card-author-rating">rating 1</div>
                    <div class="project-card-status">
                        <img src="${pageContext.request.contextPath}/images/project-card-status.jpg">
                    </div>
                    <div class="project-card-details">
                        <div>IRS: 7009$</div>
                        <div>Days to deaddivne: 13</div>
                        <div>Share price: 1$</div>
                        <div>Upreders: 433</div>
                        <div>Shares total: 7009</div>
                        <div>Shares remaining: 6000</div>
                        <div>Opened 12445 times</div>
                        <div>Publishers interested: 3</div>
                    </div>
                </div>
            </div>
        </div>

        <hr/>

        <div class="global-stats">
            <table class="global-stats-table">
                <tr>
                    <th>Projects</th>
                    <th>Successful</th>
                    <th>Upreaders</th>
                    <th>Authors</th>
                    <th>Sold e-Books</th>
                </tr>
                <tr>
                    <td>5487</td>
                    <td>562</td>
                    <td>562</td>
                    <td>50254</td>
                    <td>4056</td>
                </tr>
            </table>
        </div>

        <hr/>

        <div class="projects-wrapper">
            <div class="project-card">
                <div class="project-card-contents">
                    <div class="project-card-title">O carte cu un titlu prea lung pentru un singur rand</div>
                    <div class="project-card-cover">
                        <img class="user-profile-photo-img"
                             src="http://lorempixum.com/q/170/190/people/game%20of%20chairs"/>
                    </div>
                    <div class="project-card-author">Alexandru Octavian David Protopopitoricescovici</div>
                    <div class="project-card-author-rating">rating 1</div>
                    <div class="project-card-status">
                        <img src="${pageContext.request.contextPath}/images/project-card-status.jpg">
                    </div>
                    <div class="project-card-details">
                        <div>IRS: 7009$</div>
                        <div>Days to deaddivne: 13</div>
                        <div>Share price: 1$</div>
                        <div>Upreders: 433</div>
                        <div>Shares total: 7009</div>
                        <div>Shares remaining: 6000</div>
                        <div>Opened 12445 times</div>
                        <div>Publishers interested: 3</div>
                    </div>
                </div>
            </div>
            <div class="project-card">
                <div class="project-card-contents">
                    <div class="project-card-title">O carte cu un titlu prea lung pentru un singur rand</div>
                    <div class="project-card-cover">
                        <img class="user-profile-photo-img"
                             src="http://lorempixum.com/q/170/190/people/game%20of%20chairs"/>
                    </div>
                    <div class="project-card-author">Alexandru Octavian David Protopopitoricescovici</div>
                    <div class="project-card-author-rating">rating 1</div>
                    <div class="project-card-status">
                        <img src="${pageContext.request.contextPath}/images/project-card-status.jpg">
                    </div>
                    <div class="project-card-details">
                        <div>IRS: 7009$</div>
                        <div>Days to deaddivne: 13</div>
                        <div>Share price: 1$</div>
                        <div>Upreders: 433</div>
                        <div>Shares total: 7009</div>
                        <div>Shares remaining: 6000</div>
                        <div>Opened 12445 times</div>
                        <div>Publishers interested: 3</div>
                    </div>
                </div>
            </div>
            <div class="project-card">
                <div class="project-card-contents">
                    <div class="project-card-title">O carte cu un titlu prea lung pentru un singur rand</div>
                    <div class="project-card-cover">
                        <img class="user-profile-photo-img"
                             src="http://lorempixum.com/q/170/190/people/game%20of%20chairs"/>
                    </div>
                    <div class="project-card-author">Alexandru Octavian David Protopopitoricescovici</div>
                    <div class="project-card-author-rating">rating 1</div>
                    <div class="project-card-status">
                        <img src="${pageContext.request.contextPath}/images/project-card-status.jpg">
                    </div>
                    <div class="project-card-details">
                        <div>IRS: 7009$</div>
                        <div>Days to deaddivne: 13</div>
                        <div>Share price: 1$</div>
                        <div>Upreders: 433</div>
                        <div>Shares total: 7009</div>
                        <div>Shares remaining: 6000</div>
                        <div>Opened 12445 times</div>
                        <div>Publishers interested: 3</div>
                    </div>
                </div>
            </div>
            <div class="project-card">
                <div class="project-card-contents">
                    <div class="project-card-title">O carte cu un titlu prea lung pentru un singur rand</div>
                    <div class="project-card-cover">
                        <img class="user-profile-photo-img"
                             src="http://lorempixum.com/q/170/190/people/game%20of%20chairs"/>
                    </div>
                    <div class="project-card-author">Alexandru Octavian David Protopopitoricescovici</div>
                    <div class="project-card-author-rating">rating 1</div>
                    <div class="project-card-status">
                        <img src="${pageContext.request.contextPath}/images/project-card-status.jpg">
                    </div>
                    <div class="project-card-details">
                        <div>IRS: 7009$</div>
                        <div>Days to deaddivne: 13</div>
                        <div>Share price: 1$</div>
                        <div>Upreders: 433</div>
                        <div>Shares total: 7009</div>
                        <div>Shares remaining: 6000</div>
                        <div>Opened 12445 times</div>
                        <div>Publishers interested: 3</div>
                    </div>
                </div>
            </div>

            <div class="fb-overlay">
                <div class="fb-overlay-img">
                    <img src="${pageContext.request.contextPath}/images/home-fb-connect-1.jpg"/>
                </div>
                <div class="fb-overlay-img">
                    <img src="${pageContext.request.contextPath}/images/home-fb-connect-2.jpg"/>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="inc/footer.jspf"/>

</body>
</html>