var oTable;
var oCache = {
    iCacheLower: -1
};

google.load('visualization', '1.0', {'packages': ['corechart']});
google.setOnLoadCallback(drawTable);

function drawTable() {
    $(function(){
    oTable = $('#item_table').dataTable({
        "bFilter": false,
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": '/upreader/i/s/m',
        "bAutoWidth": false,
        "bScrollInfinite": true,
        "bScrollCollapse": true,
        "sScrollY": "400px",
        "iDisplayLength": 50,
        "fnCreatedRow": fnRowCreated,
        //"fnServerData": fnDataTablesPipeline,
        "aoColumns": [
            { "mData": "id", "bVisible": false },
            { "mData": "title", "sDefaultContent": "", "mRender": fnRenderTitle },
            { "mData": "authorName", "sDefaultContent": "", "mRender": fnRenderAuthor },
            { "mData": "bookPrice", "sDefaultContent": "" },
            { "mData": "upreaders", "sDefaultContent": "" },
            { "mData": "shareValue", "sDefaultContent": "" },
            { "mData": "irsProgress", "sDefaultContent": "", "mRender": fnRenderIrsProgress  },
            { "mData": "daysToDeadline", "sDefaultContent": "" },
            { "mData": "noViews", "sDefaultContent": "" },
            { "mData": "booksSold", "sDefaultContent": "" },
            { "mData": "sharesToSale", "sDefaultContent": "" },
            { "mData": "subscribers", "sDefaultContent": "", "mRender": fnRenderSubscribers },
            { "mData": "serialStorySubscriptionPrice", "sDefaultContent": "" },
            { "mData": "income", "sDefaultContent": "" },
            { "mData": "derivatives", "sDefaultContent": "", "mRender": fnRenderDerivatives }
        ]
    });
    });
}

function fnGetRowDetails(row) {
    var aData = oTable.fnGetData(row);
    var sOut = '<div class="mboard-details-inner">';
    sOut += '<p>Project title: ' + aData.title + '</p>';
    sOut += '<p>Author: ' + aData.authorName + '</p>';
    sOut += '<p>Book Price: ' + aData.bookPrice + '</p>';
    sOut += '<p>Derivatives: ' + aData.derivatives + '</p>';
    sOut += '</div>';

    return sOut;
}

function fnRowCreated(row, aData, iDisplayIndex) {
    drawIRSProgress(row, aData);
    drawSubscriptionProgress(row, aData);

    $(row).click(function (e) {
        if (oTable.fnIsOpen(this)) {
            var openRow = this;
            $('div.mboard-details-inner', $(row).next()).slideUp(function () {
                oTable.fnClose(openRow);
            });
        } else {
            var nDetailsRow = oTable.fnOpen(this, fnGetRowDetails(this), "mboard-info-row");
            $('div.mboard-details-inner', nDetailsRow).slideDown();
        }
    });
}

function drawIRSProgress(row, aData) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '');
    data.addColumn('number', 'IRS Progress');
    data.addRows([
        ['', aData.irsProgress]
    ]);
    var options = {
        width: 75, height: 20,
        legend: 'none',
        backgroundColor: { fill: "none" },
        hAxis: {
            baselineColor: "none",
            minValue: 0,
            maxValue: aData.irsMax,
            gridlines: {count: 0}
        }
    };

    var container = $(row).find("#irschart_" + aData.id)[0];
    var chart = new google.visualization.BarChart(container);
    chart.draw(data, options);
}

function drawSubscriptionProgress(row, aData) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '');
    data.addColumn('number', 'Subscribers');
    data.addRows([
        ['', aData.irsProgress]
    ]);
    var options = {
        width: 75, height: 20,
        legend: 'none',
        backgroundColor: { fill: "none" },
        hAxis: {
            baselineColor: "none",
            minValue: 0,
            maxValue: 1000,
            gridlines: {count: 0}
        }
    };

    var container = $(row).find("#subchart_" + aData.id)[0];
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
    return '<div id="irschart_' + row.id + '" class="irs-chart"></div>';
}

function fnRenderSubscribers(data, type, row) {
    return '<div id="subchart_' + row.id + '" class="sub-chart"></div>';
}

function fnRenderDerivatives(data, type, row) {
    return data;
}

function fnSetKey(aoData, sKey, mValue) {
    for (var i = 0, iLen = aoData.length; i < iLen; i++) {
        if (aoData[i].name == sKey) {
            aoData[i].value = mValue;
        }
    }
}

function fnGetKey(aoData, sKey) {
    for (var i = 0, iLen = aoData.length; i < iLen; i++) {
        if (aoData[i].name == sKey) {
            return aoData[i].value;
        }
    }
    return null;
}

function fnDataTablesPipeline(sSource, aoData, fnCallback) {
    var iPipe = 5;
    /* Ajust the pipe size */

    var bNeedServer = false;
    var sEcho = fnGetKey(aoData, "sEcho");
    var iRequestStart = fnGetKey(aoData, "iDisplayStart");
    var iRequestLength = fnGetKey(aoData, "iDisplayLength");
    var iRequestEnd = iRequestStart + iRequestLength;
    oCache.iDisplayStart = iRequestStart;

    /* outside pipeline? */
    if (oCache.iCacheLower < 0 || iRequestStart < oCache.iCacheLower || iRequestEnd > oCache.iCacheUpper) {
        bNeedServer = true;
    }

    /* sorting etc changed? */
    if (oCache.lastRequest && !bNeedServer) {
        for (var i = 0, iLen = aoData.length; i < iLen; i++) {
            if (aoData[i].name != "iDisplayStart" && aoData[i].name != "iDisplayLength" && aoData[i].name != "sEcho") {
                if (aoData[i].value != oCache.lastRequest[i].value) {
                    bNeedServer = true;
                    break;
                }
            }
        }
    }

    /* Store the request for checking next time around */
    oCache.lastRequest = aoData.slice();

    if (bNeedServer) {
        if (iRequestStart < oCache.iCacheLower) {
            iRequestStart = iRequestStart - (iRequestLength * (iPipe - 1));
            if (iRequestStart < 0) {
                iRequestStart = 0;
            }
        }

        oCache.iCacheLower = iRequestStart;
        oCache.iCacheUpper = iRequestStart + (iRequestLength * iPipe);
        oCache.iDisplayLength = fnGetKey(aoData, "iDisplayLength");
        fnSetKey(aoData, "iDisplayStart", iRequestStart);
        fnSetKey(aoData, "iDisplayLength", iRequestLength * iPipe);

        $.getJSON(sSource, aoData, function (json) {
            /* Callback processing */
            oCache.lastJson = jQuery.extend(true, {}, json);

            if (oCache.iCacheLower != oCache.iDisplayStart) {
                json.aaData.splice(0, oCache.iDisplayStart - oCache.iCacheLower);
            }
            json.aaData.splice(oCache.iDisplayLength, json.aaData.length);

            fnCallback(json)
        });
    }
    else {
        json = jQuery.extend(true, {}, oCache.lastJson);
        json.sEcho = sEcho;
        /* Update the echo for each response */
        json.aaData.splice(0, iRequestStart - oCache.iCacheLower);
        json.aaData.splice(iRequestLength, json.aaData.length);
        fnCallback(json);
        return;
    }
}