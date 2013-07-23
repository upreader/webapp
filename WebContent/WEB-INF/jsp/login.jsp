<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css" media="screen"/>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/js/jquery.leanModal.min.js"></script>
</head>
<body>
<jsp:include page="inc/header.jspf"/>

<div style="background-color:white; min-height: 500px;">
    <div id="loginmodal">
        <FORM id="loginform" action="${pageContext.request.contextPath}/j_security_check" method="post">
            <label for="j_username">Username:</label>
            <input type="text" name="j_username" id="j_username" class="txtfield" tabindex="1">
            <label for="j_password">Password:</label>
            <input type="password" name="j_password" id="j_password" class="txtfield" tabindex="2">
            <input type="checkbox"/>Remember me
            <div>&nbsp;</div>
            <div class="center">
                <a id="loginbtn" class="button orange flat" tabindex="3" href="#">Login</a>
                <a class="button blue flat" href="<c:url value='/i/loginWithFacebook'/>">Facebook</a>
                <a class="button blue flat" href="<c:url value='/i/loginWithTwitter'/>">Twitter</a>
            </div>
            <a href="#">Forgot my password</a>
            <input type="hidden" name="j_uri" value="i/loginSuccessful"/>
        </FORM>
    </div>
</div>

<script type="text/javascript">
<c:if test="${not empty param.failed}">
    var loginError = true;
</c:if>

$(function () {
    $('#loginbtn').click(function() {
        $('#loginform').submit();
    });

    if(loginError) {
        $('#j_username').css('border-color','red').focus();
        $('#j_password').css('border-color','red');
    }

    $('#j_username').keypress(function(event) {
        if(event.keyCode == 13) {
            $('#loginbtn').trigger('click');
        }
    });
    $('#j_password').keypress(function(event) {
        if(event.keyCode == 13) {
            $('#loginbtn').trigger('click');
        }
    });
});
</script>
<jsp:include page="inc/footer.jspf"/>
</body>
</html>
