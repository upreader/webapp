<%@page language="java" contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upreader</title>
    <jsp:include page="inc/head.jspf"/>
    <script src="//ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>
    <link rel="Stylesheet" type="text/css" href="//ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css" media="screen"/>

    <script type="text/javascript" src="https://www.google.com/jsapi"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.css" media="screen"/>
    <link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datatable.css" media="screen"/>
</head>
<body>

<jsp:include page="inc/header.jspf"/>

<div class="page page-home">
    <div class="home-container">
        <div class="home-content">
            <div class="monitor-board">
                <c:url var="datasource" value="/i/s/m"/>
                <table class="datatable" id="item_table">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Project</th>
                        <th>Author</th>
                        <th>Book Price #</th>
                        <th>Upreaders #</th>
                        <th>Share Value $</th>
                        <th>IRS Progress</th>
                        <th>Days to DL</th>
                        <th>Opened #</th>
                        <th>Sold Books #</th>
                        <th>Shares for sale #</th>
                        <th>Subscribers #</th>
                        <th>Subscr. Price $</th>
                        <th>Income $</th>
                        <th>Derivatives</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="9" class="dataTables_empty">Loading data from server</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var oTable, oSettings;

    $(function () {
    });

    google.load('visualization', '1.0', {'packages':['corechart']});
    google.setOnLoadCallback(drawTable);

    function drawTable() {
        oTable = $('#item_table').dataTable({
            "bFilter": false,
            "bProcessing": true,
            "bServerSide": true,
            "sAjaxSource": "${datasource}",
            "bAutoWidth": false,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "500px",
            "fnCreatedRow" : fnRowCreated,
            "aoColumns": [
                { "mData": "id", "bVisible": false },
                { "mData": "title", "sDefaultContent": "", "mRender":fnRenderTitle },
                { "mData": "authorName", "sDefaultContent": "","mRender":fnRenderAuthor },
                { "mData": "bookPrice", "sDefaultContent": "" },
                { "mData": "upreaders", "sDefaultContent": "" },
                { "mData": "shareValue", "sDefaultContent": "" },
                { "mData": "irsProgress","sDefaultContent": "","mRender":fnRenderIrsProgress  },
                { "mData": "daysToDeadline","sDefaultContent": "" },
                { "mData": "noViews", "sDefaultContent": "" },
                { "mData": "booksSold", "sDefaultContent": "" },
                { "mData": "sharesToSale", "sDefaultContent": "" },
                { "mData": "subscribers", "sDefaultContent": "","mRender":fnRenderSubscribers },
                { "mData": "serialStorySubscriptionPrice", "sDefaultContent": "" },
                { "mData": "income", "sDefaultContent": "" },
                { "mData": "derivatives", "sDefaultContent": "","mRender":fnRenderDerivatives }
            ]
        });
    }

    function fnRowCreated(row, aData, iDisplayIndex) {
        drawIRSProgress(row, aData);
        drawSubscriptionProgress(row, aData);
    }

    function drawIRSProgress(row, aData) {
        var data = new google.visualization.DataTable();
        data.addColumn('string', '');
        data.addColumn('number', 'IRS Progress');
        data.addRows([['',aData.irsProgress]]);
        var options = {
            width:150, height:30,
            legend: 'none',
            backgroundColor: { fill: "none" },
            hAxis: {
                baselineColor: "none",
                minValue: 0,
                maxValue: aData.irsMax,
                gridlines: {count: 0}
            }
        };

        var container = $(row).find("#irschart_"+aData.id)[0];
        var chart = new google.visualization.BarChart(container);
        chart.draw(data, options);
    }

    function drawSubscriptionProgress(row, aData) {
        var data = new google.visualization.DataTable();
        data.addColumn('string', '');
        data.addColumn('number', 'Subscribers');
        data.addRows([['',aData.irsProgress]]);
        var options = {
            width:150, height:30,
            legend: 'none',
            backgroundColor: { fill: "none" },
            hAxis: {
                baselineColor: "none",
                minValue: 0,
                maxValue: 1000,
                gridlines: {count: 0}
            }
        };

        var container = $(row).find("#subchart_"+aData.id)[0];
        var chart = new google.visualization.BarChart(container);
        chart.draw(data, options);
    }

    function fnRenderTitle(data, type, row) {
        return data;
    }

    function fnRenderAuthor(data, type, row) {
        return data;
    }

    function fnRenderIrsProgress(data, type, row) {
        return '<div id="irschart_'+row.id+'" class="irs-chart"></div>';
    }

    function fnRenderSubscribers(data, type, row) {
        return '<div id="subchart_'+row.id+'" class="sub-chart"></div>';
    }

    function fnRenderDerivatives(data, type, row) {
        return data;
    }
</script>

<jsp:include page="inc/footer.jspf"/>

</body>
</html>