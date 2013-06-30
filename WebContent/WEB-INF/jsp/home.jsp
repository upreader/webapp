<?xml version="1.0" encoding="UTF8" ?>
<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<title>Upreader</title>
<link rel="Stylesheet" type="text/css" href="css/upreader.css" media="screen" />
<script type="text/javascript" src="js/jquery-1.10.1.min.js"></script>
</head>
<body>
	<FORM action="j_security_check" method="post">
		Username: <INPUT type="text" name="j_username" /> 
		<br /> 
		Password: <INPUT type="password" name="j_password" /> 
		<br /> 
		<INPUT type="submit" value="Login" /> <input type="hidden" name="j_uri" value="loginSuccessful" />
	</FORM>
</body>
</html>