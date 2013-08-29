<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Upreader</title>
<jsp:include page="../inc/head.jspf" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css"  media="screen"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css"  media="screen"/>
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datatable.css" media="screen" />
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.raty.min.js"></script>

</head>
<body>
<jsp:include page="../inc/header.jspf" />


<div id="wrapper">
	<div id="admin-banner-welcome">
		<h1 style="margin-top:10px; font-size:30px;">Upreader Administration</h1>	
	</div>
	<div class="colmask leftmenu">
		<div class="colleft">
			<div class="col1">
				<h2>Users</h2>

				<div>
					<a id="newusr" href="${pageContext.request.contextPath}/p/admin/addUser">New</a>
					&nbsp;
					<a id="edusr" style="display: none;" href="#" onclick="javascript: editSelected()">Edit</a>
					&nbsp;
					<a id="delusr" style="display: none;" href="#" onclick="javascript: deleteSelected()">Delete</a>
				</div>
				<br/>

				<div id="entityList">
					<datatables:table id="userList" url="${pageContext.request.contextPath}/i/s/u?do=lst" serverSide="true" processing="true" cdn="true">
					   <datatables:column title="Id" property="id"/>
					   <datatables:column title="Email" property="email" />
					   <datatables:column title="Rating" property="rating" />
					   <datatables:column title="Roles" property="roles" />
					   <datatables:callback type="createdrow" function="fnAddRowSelection" />
					</datatables:table>
				</div>

				<form id="edform" action="${pageContext.request.contextPath}/p/admin/editUser" method="post">
					<input id="edobjid" type="hidden" name="objid" />
				</form>
			</div>
			<div class="col2">
				<%@ include file="../admin/nav.jspf"%>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
function fnAddRowSelection(row, aData, iDisplayIndex) {
	$(row).attr("objId", aData.id);
	$(row).click(function(e) {
		if ( $(this).hasClass('row_selected') ) {
            $(this).removeClass('row_selected');
			$('#edusr').hide();
			$('#delusr').hide();
        }
        else {
            oTable_userList.$('tr.row_selected').removeClass('row_selected');
            $(this).addClass('row_selected');
			$('#edusr').show();
			$('#delusr').show();
        }
    });
}

function editSelected() {
	var selection = oTable_userList.$('tr.row_selected');
	if(selection.length > 0) {
		var row = selection[0];
		var objid = $(row).attr('objid');
		$("#edobjid").val(objid);
		$("#edform").submit();
	}
}

function deleteSelected() {
	var selection = oTable_userList.$('tr.row_selected');
	if(selection.length > 0) {
		var row = selection[0];
		var objid = $(row).attr('objid');
		$.post('${pageContext.request.contextPath}/i/s/u', { 'objid': objid, 'do' : 'del' }, function(data) {
			if(data.message == "OK") {
				location.href='${pageContext.request.contextPath}/p/admin/users';
			}
			else
				alert('Error deleting user: '+data.message);
		}, "json");
	}
}

</script>

<jsp:include page="../inc/footer.jspf" />

</body>
</html>