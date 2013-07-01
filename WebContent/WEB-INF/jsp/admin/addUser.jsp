<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/jsp/templates"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="datatables" 	uri="http://github.com/dandelion/datatables"%>

<t:adminPage>
<jsp:body>

<h2>Create a new user</h2>

<form id="addform" action="${pageContext.request.contextPath}/i/service/addUser" method="post">
<table width="80%">
	<tr><td width="20%" align="right">Username:</td><td align="left"><input type="text" name="username"/></td></tr>
	<tr><td align="right">Email:</td><td align="left"><input type="text" name="email"/></td></tr>
	<tr><td align="right">Password:</td><td align="left"><input id="password" type="password" name="password"/></td></tr>
	<tr><td align="right">Confirm password:</td><td align="left"><input id="password2" type="password"/></td></tr>
	<tr><td align="right">Role:</td><td align="left">
		<select name="role">
			<option value="basic">Basic</option>
			<option value="buyer">Buyer</option>
			<option value="upreader">Upreader</option>
			<option value="editor">Editor</option>
			<option value="admin">Admin</option>
		</select>
	</td></tr>
	<tr><td align="right">&nbsp;</td><td>&nbsp;</td></tr>
	<tr><td align="right"><input id="btnSubmit" type="button" value="Create"/></td><td>&nbsp;</td></tr>
</table>
</form>

<script type="text/javascript">
$(document).ready(function() {
	$("#btnSubmit").click(function() {
		var psw1 = $("#password").val();
		var psw2 = $("#password2").val();
		
		if(psw1.trim().length == 0)
			alert("Password cannot be empty.");
		
		if(psw1 != psw2)
			alert("Passwords don't match.");
		else
			$("#addform").submit();
	});
});
</script>

</jsp:body>
</t:adminPage>
