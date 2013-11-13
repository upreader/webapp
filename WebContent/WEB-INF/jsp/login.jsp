<%@ page import="com.upreader.UpreaderConstants" %>
<%@ page import="com.upreader.UpreaderRequest" %>
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
<body class="container">

<jsp:include page="inc/header.jspf"/>

<%
    String serverPath = UpreaderConstants.UPREADER_SECURE_HOST + UpreaderConstants.UPREADER_CONTEXT;
%>
<div class="row login-sections">
    <section class="col-xs-3 login-section">
        <div class="login-form">
            <form id="login-form" action="<%= serverPath %>/j_security_check" method="post" autocomplete="on">
                <div class="login-form-header-title  roboto-bold-text">Log in</div>
                <div class="login-form-header">If you already have an <span class="upreader-blue-text roboto-black-text">UPREADER</span> account, please log in here.</div>
                <div class="form-vertical-item">
                    <label class="roboto-regular-text">Email</label>
                    <input type="text" id="login-email" class="form-input" name="j_username" placeholder="@"/>
                </div>
                <div class="form-vertical-item">
                    <label class="roboto-regular-text">Password</label>
                    <input type="password" id="login-password" class="form-input" name="j_password"/>
                </div>
                <div class="form-vertical-item">
                    <a class="login-forgot-password" href="">I forgot my password</a>
                </div>
                <div class="form-vertical-item">
                    <input type="checkbox" class="form-checkbox" name="remember"> <label class="roboto-regular-text">Remember me next time.</label>
                </div>
                <div class="form-vertical-item">
                    <input id="login-button" type="button" class="green-button-link" value="Log me in"/>
                </div>

                <input type="submit" value="Login" style="visibility: hidden"/>
                <input type="hidden" name="j_uri" value="i/loginSuccessful"/>
            </form>
        </div>
    </section>
    <section class="col-xs-5 signup-section">
        <div class="register-div">
            <div class="register-form">
                <form id="register-form" action="<%= serverPath %>/i/register" method="post" autocomplete="off">
                    <div class="login-form-header-title  roboto-bold-text">New around here?</div>
                    <div class="login-form-header">This is where you create your <span class="upreader-blue-text roboto-black-text">UPREADER</span> account. Should be very easy for you.</div>
                    <div class="sign-up-form-items">
                        <div class="form-vertical-item">
                            <label class="roboto-regular-text">First Name</label>
                            <input type="text" class="form-input" id="firstName" name="firstName" autocomplete="off"/>
                        </div>
                        <div class="form-vertical-item">
                            <label class="roboto-regular-text">Last Name</label>
                            <input type="text" class="form-input" id="lastName" name="lastName" autocomplete="off"/>
                        </div>
                        <div class="form-vertical-item">
                            <label class="roboto-regular-text">Email</label>
                            <input type="text" class="form-input" id="email" name="email" placeholder="@"
                                   autocomplete="off"/>
                            <div class="form-input-hint">
                                <span>We'll keep this safe, don't worry.</span>
                            </div>
                        </div>
                        <div class="form-vertical-item">
                            <label class="roboto-regular-text">Re-type your Email</label>
                            <input type="text" class="form-input" id="retype-email" name="retype-email" placeholder="@"
                                   autocomplete="off"/>
                        </div>
                        <div class="form-vertical-item">
                            <label class="roboto-regular-text">Password</label>
                            <input type="password" class="form-input" id="password" name="password" autocomplete="off"/>
                            <div class="form-input-hint roboto-regular-text">
                                <span>Make sure it's something hard to guess.</span>
                            </div>
                        </div>
                        <div class="form-vertical-item">
                            <label class="roboto-regular-text">Re-type Password</label>
                            <input type="password" class="form-input" id="retype-password" name="retype-password" autocomplete="off"/>
                        </div>
                        <div class="signup-updates-section">
                            <input type="checkbox" class="form-checkbox" id="updateMe" name="updateMe" checked="checked">
                            <label class="roboto-regular-text">Do you want us to keep you posted with any news from UPREADER?</label>
                            <p class="roboto-regular-text">By clicking on the button below and signing up, you agree
                                to our <a href="" class="upreader-blue-text">terms of use</a> and <a href="" class="upreader-blue-text">the privacy policy</a>.</p>
                        </div>
                        <div class="form-vertical-item">
                            <input id="register-button" type="button" class="green-button-link" value="I'm done, sign me up!"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
    <section class="col-xs-4 fbook-section">
        <div class="register-social">
            <div class="login-form-header-title  roboto-bold-text">Connect with facebook</div>
            <div class="login-form-header">This is the fast and easy way. If you have a Facebook account then you’re just a few clicks
                away from your very own <span class="upreader-blue-text roboto-black-text">UPREADER</span> account.</div>
            <div class="register-social-fb">
                <a href="<c:url value='/i/loginWithFacebook'/>"><img
                          src="${pageContext.request.contextPath}/img/icons/social/facebook.png"/></a>
            </div>
            <div class="login-form-header">...oh yes, we’ll play nice and won’t harass any of your friends or post anything without your permission.</div>
        </div>
    </section>

</div>

<div class="row upreader-status-data">
    <div class="col-xs-12">
        <div class="col-xs-3  nopadding">
            <div class="upreader-status-data-title  roboto-light-text">Successful</div>
            <div class="upreader-status-data-value  roboto-bold-text">5234</div>
        </div>
        <div class="col-xs-2  nopadding">
            <div class="upreader-status-data-title  roboto-light-text">Upreaders</div>
            <div class="upreader-status-data-value  roboto-bold-text">335123</div>
        </div>
        <div class="col-xs-2  nopadding">
            <div class="upreader-status-data-title  roboto-light-text">Authors</div>
            <div class="upreader-status-data-value  roboto-bold-text">424</div>
        </div>
        <div class="col-xs-2  nopadding">
            <div class="upreader-status-data-title  roboto-light-text">E&minus;books&nbsp;sold</div>
            <div class="upreader-status-data-value  roboto-bold-text">424</div>
        </div>
        <div class="col-xs-3  nopadding">
            <div class="upreader-status-data-title  roboto-light-text">Total Projects</div>
            <div class="upreader-status-data-value  roboto-bold-text">145523</div>
        </div>
    </div>
</div>

<div class="row before-footer-section">

</div>
<jsp:include page="inc/footer.jspf"/>

<script type="text/javascript">
    var loginError = false;
    var registerError = false;
    var errorReason = '';

    <c:if test="${not empty param.failed}">
        loginError = true;
    </c:if>

    <c:if test="${not empty param.regfailed}">
        registerError = true;
        errorReason = '<c:out value="${param.reason}"/>';
    </c:if>


    $(function () {
        $('#register-button').click(function () {
            // validate form
            if($('#firstName').val().trim().length > 0
                    && $('#lastName').val().trim().length > 0
                    && $('#email').val().trim().length > 0
                    && $('#password').val().trim().length > 0
                    && $('#countryCity').val().trim().length > 0) {
                $('#register-form').submit();
            } else {
                showRegisterError();
            }

        });

        $('#login-button').click(function () {
            // validate form

            if($('#login-email').val().trim().length > 0
                    && $('#login-password').val().trim().length > 0) {
                $('#login-form').submit();
            } else {
                showLoginError();
            }
        });

        if (loginError) {
            showLoginError();
        }

        if(registerError) {
            showRegisterError();
            $('#register-errors').css('display', 'block');
            $('#reg-error-list').append('<li>'+errorReason+'</li>');
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

    function showLoginError() {
        $('#login-email').css('border-color', 'red').focus();
        $('#login-password').css('border-color', 'red');
    }

    function showRegisterError() {
        if($('#firstName').val().trim().length == 0)
            $('#firstName').css('border-color', 'red');

        if($('#lastName').val().trim().length == 0)
            $('#lastName').css('border-color', 'red');

        if($('#email').val().trim().length == 0)
            $('#email').css('border-color', 'red');

        if($('#password').val().trim().length == 0)
            $('#password').css('border-color', 'red');

        if($('#countryCity').val().trim().length == 0)
            $('#countryCity').css('border-color', 'red');
    }
</script>
</body>
</html>
