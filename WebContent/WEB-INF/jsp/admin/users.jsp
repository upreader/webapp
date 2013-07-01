<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/jsp/templates"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="datatables" 	uri="http://github.com/dandelion/datatables"%>

<t:adminPage>
<jsp:body>

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
	<datatables:table id="userList" url="${pageContext.request.contextPath}/i/service/userList" serverSide="true" processing="true" cdn="true">
	   <datatables:column title="Id" property="id" />
	   <datatables:column title="Username" property="username" />
	   <datatables:column title="Email" property="email" />
	   <datatables:column title="Role" property="role" />
	   <datatables:callback type="createdrow" function="fnAddRowSelection" />
	</datatables:table>
</div>

<form id="edform" action="${pageContext.request.contextPath}/p/admin/editUser" method="post">
	<input id="edobjid" type="hidden" name="objid" />
</form>


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
		$.post('${pageContext.request.contextPath}/i/service/deleteUser', { objid: objid }, function(data) {
			if(data.message == "OK")
				location.reload();
		}, "json");
	}
}

</script>

</jsp:body>
</t:adminPage>