<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/jsp/templates"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:adminPage>
	<jsp:body>
<h2>Users</h2>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#entityTable').dataTable({
			"bProcessing" : true,
			"bServerSide" : true,
			"sAjaxSource" : "service/userList"
		});
	});
</script> 

<div id="entityList">
<table cellpadding="0" cellspacing="0" border="0" class="display" id="entityTable">
	<thead>
		<tr>
			<th width="20%">Id</th>
			<th width="25%">Username</th>
			<th width="25%">Email</th>
			<th width="15%">Role</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="5" class="dataTables_empty">Loading data from server</td>
		</tr>
	</tbody>
</table>
</div>

</jsp:body>
</t:adminPage>