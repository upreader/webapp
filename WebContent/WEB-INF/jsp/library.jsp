<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/library.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/projectcard.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css" media="screen"/>
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-library">
<div class="page-header-wrapper">
    <div class="page-header clearfix">
        <div class="page-title">
            <h1>Library</h1>

            <p>Inspired browsing!</p>
        </div>
        <div class="page-title-desc">
            lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididune.
        </div>
    </div>

    <div class="page-stats">
        <table class="page-stats-table">
            <tr>
                <th>Projects</th>
                <th>Novel</th>
                <th>Novella</th>
                <th>Short Story</th>
                <th>Graphic</th>
                <th>Poetry</th>
            </tr>
            <tr>
                <td>5487</td>
                <td>562</td>
                <td>562</td>
                <td>50254</td>
                <td>4056</td>
                <td>70468</td>
            </tr>
        </table>
    </div>

    <div class="page-search">
        <ul>
            <li>
                <form action="#" method="post">
                    <input type="text" id="search-bar" class="search-bar" name="search" placeholder="search..."
                           autocomplete="off"/>
                </form>
            </li>
            <li>
                <select class="form-input form-select">
                    <option>sort by...</option>
                    <option>2</option>
                    <option>3</option>
                </select>
            </li>
            <li>
                <select class="form-input form-select">
                    <option>ascending</option>
                    <option>descending</option>
                </select>
            </li>
        </ul>
    </div>
</div>
<div class="library-content-wrapper">
<div class="library-content">
<div class="library-sidebar">
    <div class="sidebar-box">
        <div class="sidebar-title">
            <h1>Filters</h1>
        </div>
        <div class="sidebar-selected">
            <span class="selected-filter">fiction<span class="delete-filter">x</span></span>
            <span class="selected-filter">poetry<span class="delete-filter">x</span></span>
            <span class="selected-filter">serial stories<span class="delete-filter">x</span></span>
            <span class="selected-filter">self-help<span class="delete-filter">x</span></span>
            <span class="selected-filter">children's books<span class="delete-filter">x</span></span>
        </div>
        <div class="sidebar-filters">
            <ul class="filter-categories">
                <li>
                    <div class="filter-category-title">categories</div>
                    <ul class="filter-category-contents">
                        <li>
                            <span>fiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>nonfiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>children's books</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>poetry</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>self-help</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                    </ul>
                </li>
                <li>
                    <div class="filter-category-title">genres</div>
                    <ul class="filter-category-contents">
                        <li>
                            <span>fiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>nonfiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>children's books</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>poetry</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>self-help</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                    </ul>
                </li>
                <li>
                    <div class="filter-category-title">sub-genres</div>
                    <ul class="filter-category-contents">
                        <li>
                            <span>fiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>nonfiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>children's books</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>poetry</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>self-help</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                    </ul>
                </li>
                <li>
                    <div class="filter-category-title">author</div>
                    <ul class="filter-category-contents">
                        <li>
                            <span>fiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>nonfiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>children's books</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>poetry</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>self-help</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                    </ul>
                </li>
                <li>
                    <div class="filter-category-title">popular tags</div>
                    <ul class="filter-category-contents">
                        <li>
                            <span>fiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>nonfiction</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>children's books</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>poetry</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                        <li>
                            <span>self-help</span>
                            <input type="checkbox" class="form-checkbox"/>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="library-main">
    <div class="genre">
        <div class="genre-header">
            <ul>
                <li class="genre-title">Drama</li>
                <li class="genre-subgenres">
                    <select class="form-input form-select">
                        <option>subgenres...</option>
                        <option>subgenre1</option>
                        <option>subgenre2</option>
                    </select>
                </li>
            </ul>
        </div>
        <div class="genre-content">
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
                <div class="project-card-pin-button">
                    <input type="button" class="button-white" value="Pin to control panel"/>
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
                <div class="project-card-pin-button">
                    <input type="button" class="button-white" value="Pin to control panel"/>
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
                <div class="project-card-pin-button">
                    <input type="button" class="button-white" value="Pin to control panel"/>
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
                <div class="project-card-pin-button">
                    <input type="button" class="button-white" value="Pin to control panel"/>
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
                <div class="project-card-pin-button">
                    <input type="button" class="button-white" value="Pin to control panel"/>
                </div>
            </div>
        </div>
        <div class="genre-footer clearfix">
            <span class="genre-browse-more">browse more Drama</span>
                        <span class="genre-pagination">
                            <img src="${pageContext.request.contextPath}/images/library-prev.jpg">
                            <span>1 2 3 4 5 ...</span>
                            <img src="${pageContext.request.contextPath}/images/library-next.jpg">
                        </span>
            <span class="genre-see-all">see all Drama</span>
        </div>
    </div>
</div>
</div>
</div>
</div>

<jsp:include page="inc/footer.jspf"/>

</body>
</html>