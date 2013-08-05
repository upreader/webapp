<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css" media="screen"/>
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-login">
    <section class="login-register-section">
        <header class="status-header">
            <div class="status-outer">
                <h1 class="status-text">Your current status is OBSERVER</h1>
            </div>
         </header>
        <div class="login-register-frame">
            <section class="login-section">
                <div class="login-form">
                    <FORM id="login-form" action="https://www.upreader.com:8443/upreader/j_security_check" method="post" autocomplete="on">
                        <div class="login-form-header">Already a member ?</div>
                        <div class="login-form-title">Login</div>

                        <div class="form-vertical-item">
                            <input type="text" id="login-email" class="form-input" name="j_username" placeholder="@"/>
                            <div class="form-input-hint">
                                <span>email</span>
                            </div>
                        </div>

                        <div class="form-vertical-item">
                            <input type="password" id="login-password" class="form-input" name="j_password"/>
                            <div class="form-input-hint">
                                <div>password</div>
                                <div><a href="#">forgot what it was ?</a></div>
                            </div>
                        </div>

                        <div class="form-vertical-item">
                            <input type="checkbox" class="form-checkbox" name="remember">remember me
                        </div>

                        <div class="form-vertical-item">
                            <input id="login-button" type="button" class="button-gray" value="Login"/>
                        </div>

                        <input type="submit" value="Login" style="visibility: hidden"/>
                        <input type="hidden" name="j_uri" value="i/loginSuccessful"/>
                    </FORM>
                </div>
            </section>

            <section class="register-section">
                <div class="register-div">
                    <div class="register-social">
                        <span class="register-social-content">
                            <span class="register-social-text">sign in using:</span>
                            <span class="register-social-fb">
                                <a class="button blue flat" href="<c:url value='/i/loginWithFacebook'/>"><img src="${pageContext.request.contextPath}/images/facebook.jpg"/></a>
                            </span>
                            <span class="register-social-tw">
                                <a class="button blue flat" href="<c:url value='/i/loginWithTwitter'/>"><img src="${pageContext.request.contextPath}/images/twitter.jpg"/></a>
                            </span>
                        </span>
                    </div>
                    <div class="register-form">
                        <FORM id="register-form" action="https://www.upreader.com:8443/upreader/i/register" method="post">
                            <div class="form-vertical-item">
                                <input type="text" class="form-input" name="firstName"/>
                                <div class="form-input-hint">
                                    <span>first name</span>
                                </div>
                            </div>

                            <div class="form-vertical-item">
                                <input type="text" class="form-input" name="lastName"/>
                                <div class="form-input-hint">
                                    <span>last name</span>
                                </div>
                            </div>

                            <div class="form-vertical-item">
                                <input type="text" class="form-input" name="email" placeholder="@"/>
                                <div class="form-input-hint">
                                    <span>email</span>
                                </div>
                            </div>

                            <div class="form-vertical-item">
                                <input type="password" class="form-input" name="password"/>
                                <div class="form-input-hint">
                                    <div>password</div>
                                </div>
                            </div>

                            <div class="form-vertical-item">
                                <input type="text" class="form-input" name="countryCity"/>
                                <div class="form-input-hint">
                                    <span>country, city</span>
                                </div>
                            </div>

                            <div class="form-vertical-item">
                                <input type="checkbox" class="form-checkbox" name="remember">keep me updated
                            </div>

                            <div class="form-vertical-item">
                                <input id="register-button" type="button" class="button-gray" value="Register"/>
                            </div>
                        </FORM>
                    </div>
                </div>
            </section>

            <section class="register-help-section">
                <div class="register-help-div">
                    <div class="register-help-header">Register for a Prospector account</div>
                    <div class="register-help-body">Get full access to all information, subscribe to free
                        serial-stories, check and compare stats on you Control Board
                    </div>
                </div>
            </section>
        </div>
    </section>
</div>

<jsp:include page="inc/footer.jspf"/>

<script type="text/javascript">
    var loginError = false;

    <c:if test="${not empty param.failed}">loginError = true;
    </c:if>

    $(function () {
        $('#login-button').click(function () {
            $('#login-form').submit();
        });

        if (loginError) {
            $('#login-email').css('border-color', 'red').focus();
            $('#login-password').css('border-color', 'red');
        }

        $('#email').keypress(function (event) {
            if (event.keyCode == 13) {
                $('#login-button').trigger('click');
            }
        });
        $('#password').keypress(function (event) {
            if (event.keyCode == 13) {
                $('#login-button').trigger('click');
            }
        });
    });
</script>
</body>
</html>
