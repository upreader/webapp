<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="upreader" class="com.upreader.UpreaderApplication" scope="session"/>
<jsp:useBean id="upreaderConstants" class="com.upreader.UpreaderConstants" scope="session" />
<jsp:useBean id="projectData" type="com.upreader.beans.ViewProjectBean" class="com.upreader.beans.ViewProjectBean" scope="page">
    <jsp:setProperty name="projectData" property="request" value="${pageContext.request}" />
</jsp:useBean>
<%@ page import="java.util.ResourceBundle" %>
<%
    //Retreive the resourceBundle for the current language according to the user's locale.
    ResourceBundle upreaderResources = upreader.getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", request.getLocale());
%>

<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/viewproject.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/projectcontent.css" media="screen"/>
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-view-project">
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
<div class="project-content-wrapper">
<div class="project-content">
<div class="project-content-sidebar">
    <div class="sidebar-status">
        <ul>
            <li>
                <div class="sidebar-timeleft">
                    <h1>8/25 days left</h1>
                </div>
            </li>
            <li>
                <div class="sidebar-sharesleft">
                    <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-sharesbar.jpg">
                </div>
            </li>
            <li>
                <div class="sidebar-royaltystock image-text-box clearfix">
                    <div class="image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-royaltystock.jpg">
                    </div>
                    <div class="image-text-box-text">
                        <h1>Royalty Stock:</h1>

                        <h1>5789$</h1>
                    </div>
                </div>
            </li>
            <li>
                <div class="sidebar-sharestotal image-text-box clearfix">
                    <div class="image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-sharestotal.jpg">
                    </div>
                    <div class="image-text-box-text">
                        <h1>shares total:</h1>

                        <h1>5789</h1>
                    </div>
                </div>
            </li>
            <li>
                <div class="sidebar-sharevalue image-text-box clearfix">
                    <div class="image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-sharevalue.jpg">
                    </div>
                    <div class="image-text-box-text">
                        <h1>share value: 1$</h1>
                    </div>
                </div>
            </li>
            <li>
                <div class="sidebar-upreaders image-text-box clearfix">
                    <div class="image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-upreader.jpg">
                    </div>
                    <div class="image-text-box-text">
                        <h1>956 Upreaders</h1>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="sidebar-terms">
        <div class="sidebar-derivatives">
            <div>
                <span>Available derivatives</span>
                                        <span><img
                                                src="${pageContext.request.contextPath}/images/viewproject-sidebar-question.jpg"></span>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-derivatives.jpg">
            </div>
        </div>
        <div class="sidebar-contract">
            <p>Years given to platform: 5</p>

            <p>Royalties given to platform: 5</p>
        </div>
    </div>
    <div class="sidebar-publishers">
        <div>
            <h2>Interested Publishers</h2>
        </div>
        <div>
            <div class="sidebar-publishers-box">
                <div>
                    <span>Barnes & Noble - 2 days ago</span>
                    <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-publishereye.jpg">
                </div>
                <div>
                    <span>Pengiun - 4 days ago</span>
                    <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-publishereye.jpg">
                </div>
            </div>
        </div>
    </div>
    <div class="sidebar-promopacks">
        <h2>PROMO-PACKS</h2>
        <ul>
            <li>
                <div class="promopack image-text-box clearfix">
                    <div class="image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-addpromopack.jpg">
                    </div>
                    <div class="image-text-box-text">
                        <p>500$ small campaign</p>
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-promopackbar.jpg">
                    </div>
                </div>
            </li>
            <li>
                <div class="promopack image-text-box clearfix">
                    <div class="image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-addpromopack.jpg">
                    </div>
                    <div class="image-text-box-text">
                        <p>1000$ medium campaign</p>
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-promopackbar.jpg">
                    </div>
                </div>
            </li>
            <li>
                <div class="promopack image-text-box clearfix">
                    <div class="image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-addpromopack.jpg">
                    </div>
                    <div class="image-text-box-text">
                        <p>$5000 large campaign</p>
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-promopackbar.jpg">
                    </div>
                </div>
            </li>
            <li>
                <div class="promopack-shareholder image-text-box clearfix">
                    <div class="image-text-box-image">
                        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-question.jpg">
                    </div>
                    <div class="image-text-box-text">
                        <div>Shareholder? Promote and get rich!</div>
                        <div>Choose a promo-pack and donate</div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="sidebar-user-status">
        <h1>Your status:</h1>

        <p>Shares owned: 6</p>

        <p>Dividend value: 0$</p>

        <p>Promo-pack donation: 0$</p>

        <p>Received promo materials: 5</p>
    </div>
    <div class="sidebar-dividend-stats">
        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-dividentstats.jpg">
    </div>
    <div class="sidebar-opened image-text-box clearfix">
        <div class="image-text-box-image">
            <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-opened.jpg">
        </div>
        <div class="image-text-box-text">
            <h1>Opened 4321 times</h1>
        </div>
    </div>
    <div class="sidebar-irs-stats">
        <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-irsstats.jpg">
    </div>
    <div class="sidebar-soldbooks image-text-box clearfix">
        <div class="image-text-box-image">
            <img src="${pageContext.request.contextPath}/images/viewproject-sidebar-soldbooks.jpg">
        </div>
        <div class="image-text-box-text">
            <h1>sold books 554</h1>
        </div>
    </div>
</div>
<div class="project-content-main">
    <%@include file="/WEB-INF/jsp/inc/projectcontent.jspf"%>
</div>
</div>
</div>
</div>

<jsp:include page="inc/footer.jspf"/>

</body>
</html>