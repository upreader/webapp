<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css"  media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css"  media="screen"/>
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datatable.css" media="screen" />
</head>
<body>

<jsp:include page="inc/header.jspf" />

<div class="page page-home">
    <div class="home-container">
        <div class="home-content">
            <div class="monitor-board">
                <c:url var="datasource" value="/i/s/m" />
                <datatables:table id="projectList" url="${datasource}"
                                  serverSide="true" processing="true" cdn="true" paginate="false" autoWidth="false"
                                    filter="false">
                    <datatables:column title="Id" property="id" visible="false"/>
                    <datatables:column title="Project" property="title"/>
                    <datatables:column title="Author" property="authorName"/>
                    <datatables:column title="Book Price #" property="bookPrice"/>
                    <datatables:column title="Upreaders #" property="upreaders"/>
                    <datatables:column title="Share Value $" property="shareValue"/>
                    <datatables:column title="IRS Progress" property="irsProgress"/>
                    <datatables:column title="Days to DL" property="daysToDeadline"/>
                    <datatables:column title="Opened #" property="noViews"/>
                    <datatables:column title="Sold Books #" property="booksSold"/>
                    <datatables:column title="Shares for sale #" property="sharesToSale"/>
                    <datatables:column title="Subscribers #" property="subscribers"/>
                    <datatables:column title="Subscr. Price $" property="serialStorySubscriptionPrice"/>
                    <datatables:column title="Income $" property="income"/>
                    <datatables:column title="Derivatives" property="derivatives"/>
                    <datatables:callback type="createdrow" function="fnRowCreated" />
                </datatables:table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function fnRowCreated(row, aData, iDisplayIndex) {
        $(row).attr("objId", aData.id);
        $(row).click(function(e) {
            if ( $(this).hasClass('row_selected') ) {
                $(this).removeClass('row_selected');
            } else {
                oTable_userList.$('tr.row_selected').removeClass('row_selected');
                $(this).addClass('row_selected');
            }
        });
    }
</script>

<jsp:include page="inc/footer.jspf" />

</body>
</html>