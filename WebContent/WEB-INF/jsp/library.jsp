<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<jsp:useBean id="upreader" class="com.upreader.UpreaderApplication" scope="session"/>
<jsp:useBean id="upreaderConstants" class="com.upreader.UpreaderConstants" scope="session"/>
<jsp:useBean id="libraryData" type="com.upreader.beans.LibraryBean" class="com.upreader.beans.LibraryBean" scope="page">
    <jsp:setProperty name="libraryData" property="request" value="${pageContext.request}" />
</jsp:useBean>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="com.upreader.UpreaderConstants" %>
<%
    //Retreive the resourceBundle for the current language according to the user's locale.
    ResourceBundle upreaderResources = upreader.getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", request.getLocale());
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en" id="ng-app" ng-app="upreaderLibraryApp">
<head>
    <title><%=upreaderResources.getString("library.title")%></title>
    <jsp:include page="inc/head.jspf"/>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/library.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/projectcard.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css" media="screen"/>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/angularmodules/ngyn-select-key.js"></script>
    <script type="text/javascript" src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.6.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/upreaderLibrary.js"></script>
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-library" id="page-library" ng-controller="upreaderLibraryController">
<div class="page-header-wrapper">
    <div class="page-header clearfix">
        <div class="page-title">
            <h1><%=upreaderResources.getString("library.title")%></h1>
            <p><%=upreaderResources.getString("library.moto")%></p>
        </div>
        <div class="page-title-desc">
            lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididune.
        </div>
    </div>

    <div class="page-stats">
        <table class="page-stats-table">
            <tr>
                <th>Projects</th>
                <c:forEach var="category" items="${libraryData.projectCountByCategory}">
                    <th>
                        ${libraryData.getLocalizedCategoryResource(category.key)}
                    </th>
                </c:forEach>
            </tr>
            <tr>
                <td>${libraryData.noOfActiveProject}</td>
                <c:forEach var="category" items="${libraryData.projectCountByCategory}">
                    <th>${category.value}</th>
                </c:forEach>
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
            <h1><%=upreaderResources.getString("upreader.filters")%></h1>
        </div>
        <input type="hidden" id="localized-categories-data" value='${libraryData.toJson(libraryData.localizedCategories)}' />
        <input type="hidden" id="localized-genres-data" value='${libraryData.toJson(libraryData.localizedGenres)}' />
        <input type="hidden" id="localized-subgenres-data" value='${libraryData.toJson(libraryData.localizedSubGenres)}' />
        <input type="hidden" id="localized-authors-data" value='${libraryData.toJson(libraryData.authors)}' />
        <div class="sidebar-selected">
            <span ng-repeat="(idx, filter) in filters['category']" ng-show="!(localizedCategories[filter]|isEmpty)" class="selected-filter">
                <a ng-show="!(localizedCategories[filter]|isEmpty)" ng-click="removeFilter(idx, 'category')" ng-href="#"><span ng-bind="localizedCategories[filter]"></span><span class="delete-filter">x</span></a>
            </span>
            <span ng-repeat="(idx, filter) in filters['genre']" ng-show="!(localizedGenres[filter]|isEmpty)" class="selected-filter">
                <a ng-show="!(localizedGenres[filter]|isEmpty)" ng-click="removeFilter(idx, 'genre')" ng-href="#"><span ng-bind="localizedGenres[filter]"></span><span class="delete-filter">x</span></a>
            </span>
            <span ng-repeat="(idx, filter) in filters['subgenre']" ng-show="!(localizedSubgenres[filter]|isEmpty)" class="selected-filter">
                <a ng-show="!(localizedSubgenres[filter]|isEmpty)" ng-click="removeFilter(idx, 'subgenre')" ng-href="#"><span ng-bind="localizedSubgenres[filter]"></span><span class="delete-filter">x</span></a>
            </span>
            <span ng-repeat="(idx, filter) in filters['author']" ng-show="!(localizedAuthors[filter]|isEmpty)" class="selected-filter">
                <a ng-show="!(localizedAuthors[filter]|isEmpty)" ng-click="removeFilter(idx, 'author')" ng-href="#"><span ng-bind="localizedAuthors[filter]"></span><span class="delete-filter">x</span></a>
            </span>
        </div>
        <div class="sidebar-filters">
            <ul class="filter-categories">
                <li>
                    <div class="filter-category-title"><%=upreaderResources.getString("upreader.filters.categories")%></div>
                    <ul class="filter-category-contents">
                        <c:forEach var="category" varStatus="idx" items="${libraryData.localizedCategories}">
                            <li>
                                <span>${category.value}</span>
                                <input ng-model="filters['category'][${idx.index}]" type="checkbox" name="categoryFilter" ng-true-value="${category.key}" ng-false-value="" class="form-checkbox"/>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
                <li>
                    <div class="filter-category-title"><%=upreaderResources.getString("upreader.filters.genres")%></div>
                    <ul class="filter-category-contents">
                        <c:forEach var="genre" varStatus="idx" items="${libraryData.localizedGenres}">
                            <li>
                                <span>${genre.value}</span>
                                <input ng-model="filters['genre'][${idx.index}]" type="checkbox" name="genreFilter" ng-true-value="${genre.key}" ng-false-value="" class="form-checkbox"/>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
                <li>
                    <div class="filter-category-title"><%=upreaderResources.getString("upreader.filters.subgenres")%></div>
                    <ul class="filter-category-contents">
                        <c:forEach var="subgenre" varStatus="idx" items="${libraryData.localizedSubGenres}">
                            <li>
                                <span>${subgenre.value}</span>
                                <input ng-model="filters['subgenre'][${idx.index}]" type="checkbox" name="subgenreFilter"  ng-true-value="${subgenre.key}" ng-false-value="" class="form-checkbox"/>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
                <li>
                    <div class="filter-category-title"><%=upreaderResources.getString("upreader.filters.authors")%></div>
                    <ul class="filter-category-contents">
                        <c:forEach var="author" varStatus="idx" items="${libraryData.authors}">
                            <li>
                                <span>${author.value}</span>
                                <input ng-model="filters['author'][${idx.index}]" type="checkbox" name="authorFilter" ng-true-value="${author.key}" ng-false-value="" class="form-checkbox"/>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
                <li>
                    <div class="filter-category-title"><%=upreaderResources.getString("upreader.filters.tags")%></div>
                    <ul class="filter-category-contents">
                        <c:forEach var="tag" varStatus="idx" items="${libraryData.tags}">
                            <li>
                                <span>${tag}</span>
                                <input ng-model="filters['tag'][${idx.index}]" type="checkbox" name="tagsFilter" ng-true-value="${tag}" ng-false-value="" class="form-checkbox"/>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="library-main">
    <!-- this repeats for every genre -->
    <input type="hidden" id="genres-data" value='${libraryData.genresData(libraryData.projectsByGenre.keySet())}' />
    <c:forEach var="genre" items="${libraryData.projectsByGenre}">
        <%--<div class="genre" ng-show="filteredProjectsByGenre[${genre.key}].length > 0">--%>
        <div class="genre">
            <div class="genre-header">
                <ul>
                    <li class="genre-title">
                        ${libraryData.getLocalizedGenreResource(genre.key)}
                    </li>
                    <li class="genre-subgenres">
                        <select ng-model="subGenreFilter[${genre.key}]" class="form-input form-select">
                            <option selected="true" value=""><%=upreaderResources.getString("upreader.all")%></option>
                            <c:forEach var="subgenre" items="${libraryData.retreiveSubGenres(genre.key)}">
                                <option value="${subgenre.value}">${subgenre.label}</option>
                            </c:forEach>
                        </select>
                    </li>
                </ul>
            </div>

            <div class="genre-content">
                <input type="hidden" id="projects-for-genre-${genre.key}" value='${libraryData.projectsData(genre.value)}'/>
                    <div class="project-card" ng-repeat='project in (filteredProjectsByGenre[${genre.key}] = ( projectsByGenre[${genre.key}] | filter:subGenreFilter[${genre.key}] | filter:filterProjects ) ) | limitTo: ( currentPageByGenre[${genre.key}]  * itemsOnPage ) | limitTo: (-1*itemsOnPage) ' >
                    <div class="project-card-contents">
                        <div class="project-card-title">{{project.projectTitle}}</div>
                        <div class="project-card-cover">
                            <img class="user-profile-photo-img"
                                 src="http://lorempixum.com/q/170/190/people/game%20of%20chairs"/>
                        </div>
                        <div class="project-card-author">{{project.authorName}}</div>
                        <div class="project-card-author-rating"><%=upreaderResources.getString("upreader.rating")%> {{project.authorRating}}</div>
                        <div class="project-card-status">
                            <img src="${pageContext.request.contextPath}/images/project-card-status.jpg">
                        </div>
                        <div class="project-card-details">
                            <div><%=upreaderResources.getString("upreader.project.IRS")%> {{project.irs}}</div>
                            <div><%=upreaderResources.getString("upreader.project.daysToDeadline")%>  {{project.daysToDeadline}}</div>
                            <div><%=upreaderResources.getString("upreader.project.shareprice")%> {{project.shareValue}}<%=upreaderResources.getString("upreader.currencySign")%></div>
                            <div><%=upreaderResources.getString("upreader.project.upreaders")%> {{project.interestedUsers}}</div>
                            <div><%=upreaderResources.getString("upreader.project.sharestotal")%> {{project.totalShares}}</div>
                            <div><%=upreaderResources.getString("upreader.project.sharesRemaining")%> {{project.remainingShares}}</div>
                            <div><%=upreaderResources.getString("upreader.project.opened")%> {{project.noViews}} <%=upreaderResources.getString("upreader.times")%></div>
                            <div><%=upreaderResources.getString("upreader.project.interestedPublishers")%> {{project.interestedPublishers}}</div>
                        </div>
                    </div>
                    <div class="project-card-pin-button">
                        <input type="button" class="button-white" ng-click="showFilters()" value="Pin to control panel"/>
                    </div>
                </div>
            </div>

            <div class="genre-footer clearfix">
                <span class="genre-pagination">
                    <span class="genre-browse-more  inline-block"><%=upreaderResources.getString("upreader.browseMore")%> ${libraryData.getLocalizedGenreResource(genre.key)}</span>
                    <span class="inline-block">
                        <pagination total-items="filteredProjectsByGenre[${genre.key}].length"
                                    page="currentPageByGenre[${genre.key}]"
                                    max-size="5"
                                    items-per-page="itemsOnPage"
                                    class="pagination-small"
                                    boundary-links="true"
                                    rotate="false">
                        </pagination>
                    </span>
                    <span class="genre-see-all  inline-block"><%=upreaderResources.getString("upreader.seeAll")%> ${libraryData.getLocalizedGenreResource(genre.key)}</span>
                </span>
            </div>
        </div>

    </c:forEach>
</div>
</div>
</div>
</div>

<jsp:include page="inc/footer.jspf"/>

</body>
</html>