<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Upreader</title>
<jsp:include page="inc/head.jspf" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css"  media="screen"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css"  media="screen"/>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jquery.leanModal.min.js"></script>
</head>
<body>
<jsp:include page="inc/header.jspf" />

<div id="banner">
	<div id="banner-welcome">
		<h1 style="margin-top:200px; font-size:30px;">Welcome to Upreader</h1>
	</div>
	<c:choose>
	<c:when test="${empty pageContext.request.userPrincipal}">
	<div id="loginbar">
		<a class="button magenta flat" href="#">Create account</a>
		<a id="login" class="button orange flat" href="#loginmodal">Login</a>
	</div>
	</c:when>
	<c:otherwise>
	<div id="loginbar">
		<a class="button pink flat" href="#">My Workspace</a>
	</div>
	</c:otherwise>
	</c:choose>
</div>
<table width="50%" align="center">
	<tr>
        <td><a href="<c:url value='/p/projects'/>">Projects Page</a></td>
		<td><a href="<c:url value='/p/newproject1'/>">Create Project</a></td>
		<td><a href="<c:url value='/p/myprojects'/>">My Projects</a></td>
		<td><a href="<c:url value='/p/allprojects'/>">All Projects</a></td>
	</tr>
</table>

<div id="loginmodal" style="display:none;">
    <label for="j_username">Username:</label>
    <input type="text" id="username" class="txtfield" tabindex="1">
     
    <label for="j_password">Password:</label>
    <input type="password" id="password" class="txtfield" tabindex="2">
     
    <div class="center">
		<a id="loginbtn" class="button orange flat" tabindex="3" href="#">Login</a>
		<a class="button blue flat" href="<c:url value='/i/loginWithFacebook'/>">Facebook</a>
	</div>
</div>

<script type="text/javascript">
$(function(){
  $('#loginbtn').click(function() {
	$.ajax({
		type: "POST",
		url: "https://www.upreader.com:8443/upreader/u_login",
		async: false,
		crossDomain: true,
		data: {
			j_username: $('#username').val(),
            j_password: $('#password').val(),
			j_uri : 'http://www.upreader.com:8080/upreader/i/loginSuccessful'
        },
		success: function(data, textStatus, xhr) {
			if(data.result == 'success') {
				location.href = 'http://www.upreader.com:8080/upreader/i/loggedin?id='+data.id;
			}
			else if(data.result == 'error') {
				$('#username').css('border-color','red');
				$('#password').css('border-color','red');
				$('#username').focus();
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('Server error');
		}
	});
  });
  
  $('#login').leanModal({ top: 240, overlay: 0.45, closeButton: ".hidemodal" });
  $('#username').keypress(function(event) {
	 if(event.keyCode == 13) { 
		$('#loginbtn').trigger('click');
	 }
  });
  $('#password').keypress(function(event) {
	 if(event.keyCode == 13) { 
		$('#loginbtn').trigger('click');
	 }
  });
});
</script> 

<jsp:include page="inc/footer.jspf" />
</body>
</html>