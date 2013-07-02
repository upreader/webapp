<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@page import="com.upreader.UpreaderConstants" %>
<%@taglib prefix="t" tagdir="/WEB-INF/jsp/templates"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="datatables" 	uri="http://github.com/dandelion/datatables"%>

<t:adminPage>
<jsp:body>

<h2>Edit user</h2>

<form id="edform" action="${pageContext.request.contextPath}/i/s/u" method="post">
<table width="80%">
	<tr><td width="20%" align="right">Username:</td><td align="left"><input id="username" type="text" name="username"/></td></tr>
	<tr><td align="right">Email:</td><td align="left"><input type="text" id="email" name="email"/></td></tr>
	<tr><td align="right">Password:</td><td align="left"><input id="password" type="password" name="password"/></td></tr>
	<tr><td align="right">Confirm password:</td><td align="left"><input id="password2" type="password"/></td></tr>
	<tr><td align="right">Rating:</td><td align="left"><div id="stars"></div><input type="hidden" id="rating" name="rating"/></td></tr>
	<tr><td align="right">Role:</td><td align="left">
		<input type="checkbox" name="roles" value="prospector"/>Prospector<br />
		<input type="checkbox" name="roles" value="reader"/>Reader<br />
		<input type="checkbox" name="roles" value="upreader"/>Upreader<br />
		<input type="checkbox" name="roles" value="author"/>Author<br />
		<input type="checkbox" name="roles" value="editor"/>Editor<br />
		<input type="checkbox" name="roles" value="admin"/>Admin<br />
	</td></tr>
	<tr><td align="right">&nbsp;</td><td>&nbsp;</td></tr>
	<tr><td align="right"><input id="btnSubmit" type="button" value="Update"/></td><td>&nbsp;</td></tr>
</table>
<input type="hidden" name="objid" value="${param.objid}"/>
<input type="hidden" name="do" value="upd" />
</form>

<script type="text/javascript">
$(document).ready(function() {
	$("#btnSubmit").click(function() {
		var psw1 = $("#password").val();
		var psw2 = $("#password2").val();
		
		if(psw1 != psw2)
			alert("Passwords don't match.");
		else
			$("#edform").submit();
	});
	
	$.post('${pageContext.request.contextPath}/i/s/u', { 'objid' : "${param.objid}", 'do':'get' }, function(data) {
		$('#username').val(data.username);
		$('#email').val(data.email);
		$('#rating').val(data.rating);
		$('input[name=roles]').each(function() {
			var roles = data.roles.split(',');
			for(var i=0; i<roles.length; i++) {
				if($(this).val() == roles[i])
					$(this).attr('checked', 'checked');
			}
		});
		$('#stars').raty({
			score: data.rating,
			number: 6,
			target : '#rating',
			targetType: 'number',
			targetKeep: true,
			path: '${pageContext.request.contextPath}/images'
		});
	}, "json");
});
</script>

</jsp:body>
</t:adminPage>
