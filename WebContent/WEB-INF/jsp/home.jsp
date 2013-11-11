<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf"/>


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
    <script src="${pageContext.request.contextPath}/js/jquery.jcarousel.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jcarousel-skin.css" media="screen"/>

</head>
<body class="container">

<jsp:include page="inc/header.jspf"/>

<div class="row homepage-banner">
    <div class="col-xs-12">
        <p class="banner-upper-text">Amanda Whatsmyname, “There and back again”</p>
        <div class="banner-middle-text">
            <p>Some stories make it big.</p>
            <p>Get a monthly paycheck if they do.</p>
        </div>

        <div class="banner-lower-text">
            <p>Go Beyond reading</p>
        </div>

        <a href="#" class="green-button-link">Start a project</a>
    </div>
</div>
<div class="row homepage-main-content">
</div>
<div class="row homepage-project-cards">
<h1>Loren Ipsum</h1>

<div class="col-xs-12">
<div class="project-card">
    <img class="project-card-book-logo" src="${pageContext.request.contextPath}/img/cards/project1Logo.png"/>

    <div class="project-card-book-data">
        <p class="project-card-book-title">
            Book title here and maybe on the second row too
        </p>
        <a href="#" class="project-card-book-type-logo">
            <img src="${pageContext.request.contextPath}/img/icons/story.png"/>
        </a>
    </div>
    <div class="project-card-book-author">
        Alexandru Octavian David Brokeback
    </div>
    <div class="project-card-author-data">
        <div class="project-card-author-data-left">Author rating <img src="${pageContext.request.contextPath}/img/icons/ratings/1.png"/></div>
        <div class="project-card-author-data-right"><span class="black-text">$</span>5.5</div>
    </div>
    <div class="project-card-financial-data">
        <div class="project-card-financial-data-left">
        </div>
        <div class="project-card-financial-data-right">
            <div class="project-card-financial-data-right-value">+60</div>
            <div class="project-card-financial-data-right-label">books sold</div>
        </div>
    </div>
    <div class="project-card-detail-row-with-label">
       <span class="project-card-green-text">
           1,2$
        </span>
        <span class="project-card-small-text">
            share value
        </span>
        <span class="project-card-small-text">with</span>
        <span class="project-card-green-text">
            890
        </span>
        <span class="project-card-small-text">upreaders</span>
    </div>
    <div class="project-card-small-text project-card-line-text-separator">
        <span>with</span>
    </div>
    <div class="project-card-detail-row-with-label">
        <span class="project-card-green-text">
            4$
        </span>
        <span class="project-card-small-text">
            /month salary
        </span>

        <span class="project-card-green-text right">
            30<span class="project-card-small-text right">shares for sale</span>
        </span>

    </div>
    <div class="project-card-detail-row-with-label">
        <span class="project-card-green-text">
            9000 views
        </span>
        <span class="project-card-small-text">
        </span>
        <span class="project-card-small-text"></span>
            <span class="project-card-green-text">
            </span>
        <span class="project-card-small-text"></span>
    </div>
</div>

<div class="project-card">
    <img class="project-card-book-logo" src="${pageContext.request.contextPath}/img/cards/project1Logo.png"/>

    <div class="project-card-book-data">
        <p class="project-card-book-title">
            Book title here and maybe on the second row too
        </p>
        <a href="#" class="project-card-book-type-logo">
            <img src="${pageContext.request.contextPath}/img/icons/story.png"/>
        </a>
    </div>
    <div class="project-card-book-author">
        Alexandru Octavian David Brokeback
    </div>
    <div class="project-card-author-data">
        <div class="project-card-author-data-left">Author rating <img src="${pageContext.request.contextPath}/img/icons/ratings/1.png"/></div>
        <div class="project-card-author-data-right"><span class="black-text">$</span>5.5</div>
    </div>
    <div class="project-card-financial-data">
        <div class="project-card-financial-data-left">
        </div>
        <div class="project-card-financial-data-right">
            <div class="project-card-financial-data-right-value">+60</div>
            <div class="project-card-financial-data-right-label">books sold</div>
        </div>
    </div>
    <div class="project-card-detail-row-with-label">
        <span class="project-card-green-text">
            1,2$
        </span>
        <span class="project-card-small-text">
            share value
        </span>
        <span class="project-card-small-text">with</span>
        <span class="project-card-green-text">
            890
        </span>
        <span class="project-card-small-text">upreaders</span>
    </div>
    <div class="project-card-small-text project-card-line-text-separator">
        <span>with</span>
    </div>
    <div class="project-card-detail-row-with-label">
    <span class="project-card-green-text">
        4$
    </span>
    <span class="project-card-small-text">
        /month salary
    </span>

    <span class="project-card-green-text right">
        30<span class="project-card-small-text right">shares for sale</span>
    </span>

    </div>
    <div class="project-card-detail-row-with-label">
    <span class="project-card-green-text">
        9000 views
    </span>
    <span class="project-card-small-text">
    </span>
        <span class="project-card-small-text"></span>
        <span class="project-card-green-text">
        </span>
        <span class="project-card-small-text"></span>
    </div>
</div>

<div class="project-card">
    <img class="project-card-book-logo" src="${pageContext.request.contextPath}/img/cards/project1Logo.png"/>

    <div class="project-card-book-data">
        <p class="project-card-book-title">
            Book title here and maybe on the second row too
        </p>
        <a href="#" class="project-card-book-type-logo">
            <img src="${pageContext.request.contextPath}/img/icons/story.png"/>
        </a>
    </div>
    <div class="project-card-book-author">
        Alexandru Octavian David Brokeback
    </div>
    <div class="project-card-author-data">
        <div class="project-card-author-data-left">Author rating <img src="${pageContext.request.contextPath}/img/icons/ratings/1.png"/></div>
        <div class="project-card-author-data-right"><span class="black-text">$</span>5.5</div>
    </div>
    <div class="project-card-financial-data">
        <div class="project-card-financial-data-left">
        </div>
        <div class="project-card-financial-data-right">
            <div class="project-card-financial-data-right-value">+60</div>
            <div class="project-card-financial-data-right-label">books sold</div>
        </div>
    </div>
    <div class="project-card-detail-row-with-label">
        <span class="project-card-green-text">
            1,2$
        </span>
        <span class="project-card-small-text">
            share value
        </span>
        <span class="project-card-small-text">with</span>
        <span class="project-card-green-text">
            890
        </span>
        <span class="project-card-small-text">upreaders</span>
    </div>
    <div class="project-card-small-text project-card-line-text-separator">
        <span>with</span>
    </div>
    <div class="project-card-detail-row-with-label">
    <span class="project-card-green-text">
        4$
    </span>
    <span class="project-card-small-text">
        /month salary
    </span>

    <span class="project-card-green-text right">
        30<span class="project-card-small-text right">shares for sale</span>
    </span>

    </div>
    <div class="project-card-detail-row-with-label">
        <span class="project-card-green-text">
            9000 views
        </span>
        <span class="project-card-small-text">
        </span>
        <span class="project-card-small-text"></span>
        <span class="project-card-green-text">
        </span>
        <span class="project-card-small-text"></span>
    </div>
</div>

<div class="project-card">
    <img class="project-card-book-logo" src="${pageContext.request.contextPath}/img/cards/project1Logo.png"/>

    <div class="project-card-book-data">
        <p class="project-card-book-title">
            Book title here and maybe on the second row too
        </p>
        <a href="#" class="project-card-book-type-logo">
            <img src="${pageContext.request.contextPath}/img/icons/story.png"/>
        </a>
    </div>
    <div class="project-card-book-author">
        Alexandru Octavian David Brokeback
    </div>
    <div class="project-card-author-data">
        <div class="project-card-author-data-left">Author rating <img src="${pageContext.request.contextPath}/img/icons/ratings/1.png"/></div>
        <div class="project-card-author-data-right"><span class="black-text">$</span>5.5</div>
    </div>
    <div class="project-card-financial-data">
        <div class="project-card-financial-data-left">
        </div>
        <div class="project-card-financial-data-right">
            <div class="project-card-financial-data-right-value">+60</div>
            <div class="project-card-financial-data-right-label">books sold</div>
        </div>
    </div>
    <div class="project-card-detail-row-with-label">
        <span class="project-card-green-text">
            1,2$
        </span>
        <span class="project-card-small-text">
            share value
        </span>
        <span class="project-card-small-text">with</span>
        <span class="project-card-green-text">
            890
        </span>
        <span class="project-card-small-text">upreaders</span>
    </div>
    <div class="project-card-small-text project-card-line-text-separator">
        <span>with</span>
    </div>
    <div class="project-card-detail-row-with-label">
    <span class="project-card-green-text">
        4$
    </span>
    <span class="project-card-small-text">
        /month salary
    </span>

    <span class="project-card-green-text right">
        30<span class="project-card-small-text right">shares for sale</span>
    </span>

    </div>
    <div class="project-card-detail-row-with-label">
        <span class="project-card-green-text">
            9000 views
        </span>
        <span class="project-card-small-text">
        </span>
        <span class="project-card-small-text"></span>
        <span class="project-card-green-text">
        </span>
        <span class="project-card-small-text"></span>
    </div>
</div>
</div>
</div>

<jsp:include page="inc/footer.jspf"/>

</body>
</html>