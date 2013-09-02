<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader - Profile</title>
    <jsp:include page="inc/head.jspf"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css" media="screen"/>
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-profile">
    <div class="profile-container">
        <div class="info1 clearfix">
            <div class="info1-col1">
                <div class="info1-title">
                    <h1>My Profile</h1>
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
                    <a href="#">EDIT</a>
                </div>
            </div>
            <div class="info1-col2">
                <table class="info1-table">
                    <tr>
                        <td class="info1-form-label">First Name:</td>
                        <td class="info1-form-value">Radu</td>
                    </tr>
                    <tr>
                        <td class="info1-form-label">Last Name:</td>
                        <td class="info1-form-value">Rapeanu</td>
                    </tr>
                    <tr>
                        <td class="info1-form-label">Country:</td>
                        <td class="info1-form-value">Romania</td>
                    </tr>
                    <tr>
                        <td class="info1-form-label">State:</td>
                        <td class="info1-form-value">Bucharest</td>
                    </tr>
                    <tr>
                        <td class="info1-form-label">Zip:</td>
                        <td class="info1-form-value">764553</td>
                    </tr>
                    <tr>
                        <td class="info1-form-label">Birthday:</td>
                        <td class="info1-form-value">15 July 1982</td>
                    </tr>
                    <tr>
                        <td class="info1-form-label">Occupation:</td>
                        <td class="info1-form-value">Copywriter</td>
                    </tr>
                </table>
            </div>
            <div class="info1-col3">
                <div class="info1-col3-title">
                    <h2>Education</h2>
                </div>
                <div class="education-table-wrapper">
                    <table class="education-table">
                        <tr>
                            <td class="education-name">Best Kindergarden, Focsangeles</td>
                            <td class="education-period">01/02/1988 - 01/02/1988</td>
                        </tr>
                        <tr>
                            <td class="education-name">Better Elementary School, Focsangeles</td>
                            <td class="education-period">01/02/1988 - 01/02/1988</td>
                        </tr>
                        <tr>
                            <td class="education-name">Ultra Good Highschool, Focsangeles</td>
                            <td class="education-period">01/02/1988 - 01/02/1988</td>
                        </tr>
                        <tr>
                            <td class="education-name">SuperGigaMega MBA Bucharest</td>
                            <td class="education-period">01/02/1988 - 01/02/1988</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <hr/>
        <div class="info2 clearfix">
            <div class="info2-col1">
                <div class="experience-title">Experience:</div>
                <div class="experience-table-wrapper">
                    <table class="experience-table">
                        <tr>
                            <td>3</td>
                            <td>years</td>
                            <td>novel writer</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>year</td>
                            <td>poem writer</td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="info2-col2">
                <div class="motivation-wrapper">
                    <table class="motivation-table">
                        <tr>
                            <td class="motivation-title">Motivation:</td>
                            <td class="motivation-input">
                                <div class="motivation-display">I have a dream, to have a dream
                                    Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
                                    fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa
                                    qui officia deserunt mollit anim id est laborum. lorem ipsum dolor sit amet,
                                    consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore
                                    magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                                    nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                                    voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="inspiration-wrapper">
                    <table class="inspiration-table">
                        <tr>
                            <td class="inspiration-title">Inspiration:</td>
                            <td class="inspiration-input">
                                <span class="inspiration-tag">J.R. Tolkien</span>
                                <span class="inspiration-tag">Ion Creanga</span>
                                <span class="inspiration-tag">Jules Verne</span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <hr/>
        <div class="bio">
            <div class="bio-title">
                <h1>My Bio</h1>
            </div>
            <div class="bio-display">
                lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et
                dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
                ex ea commodo consequat.

                Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est
                laborum. lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
                ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                cillum dolore eu fugiat nulla pariatur.

                Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est
                laborum.lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
                ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa
                qui officia deserunt mollit anim id est laborum.
            </div>
        </div>
        <hr/>
    </div>
</div>

<jsp:include page="inc/footer.jspf"/>

</body>
</html>