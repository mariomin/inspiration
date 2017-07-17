$(document).ready(function() {

    var chartData = [];
    var stockEvents = [];

    var chart = AmCharts.makeChart("backtestOverview", {
        "type": "stock",
        "language": "zh",
        "theme": "light",
        "glueToTheEnd":false,
        "dataDateFormat": "YYYY-MM-DDTHH:NN:SSZ",
        "categoryAxesSettings": {
            "minPeriod": "mm"
        },
        "dataSets": [{
            "color": "#121d6d",
            "fieldMappings": [{
                "fromField": "value",
                "toField": "value"
            }, {
                "fromField": "volume",
                "toField": "volume"
            }],
            "dataProvider": chartData,
            "categoryField": "date",
            "stockEvents": stockEvents,
        }],
        "panels": [{
            "title": "盈亏",
            "stockGraphs": [{
                "id": "g1",
                "valueField": "value"
            }],
            "stockLegend": {
                "valueTextRegular": " ",
                "markerType": "none"
            }
        }],

        "chartScrollbarSettings": {
            "graph": "g1",
            "usePeriod": "mm",
        },

        "chartCursorSettings": {
            "valueBalloonsEnabled": true,
            "graphBulletSize": 1,
            "valueLineBalloonEnabled": true,
            "valueLineEnabled": true,
            "valueLineAlpha": 0.5
        },

        "periodSelector": {
            "periods": [
            {
                "period": "mm",
                "count": 10,
                "label": "10 分钟"
            }, {
                "period": "hh",
                "count": 1,
                "label": "1 小时"
            }, {
                "period": "MM",
                "count": 1,
                "label": "1 月"
            }, {
                "period": "YYYY",
                "count": 1,
                "label": "1 年"
            }, {
                "period": "MAX",
                "label": "全部",
                "selected":true
            }]
        },

        "panelsSettings": {
            "usePrefixes": true
        },
        "export": {
            "language": "zh",
            "enabled": true
        }
    });

    $.fn.dataTable.ext.errMode = 'throw';
    var tradeRecordTable = $('#tradeRecordTable').DataTable({
        language: {
            url:"../vendor/datatables/lang/zh.json"
        }
    });

    function wsUrl(s) {
        var l = window.location;
        return ((l.protocol === "https:") ? "wss://" : "ws://") + l.host + l.pathname + s;
    }


    var interval;
    var websocket;


    var websocketEchoServerUri = wsUrl("");

    var startButton = document.getElementById('startBtn');
    var endButton = document.getElementById('endBtn');
    endButton.disabled = "disabled";
    

    startButton.addEventListener('click', startDraw);
    endButton.addEventListener('click', stopDraw);

    function startDraw() {
        startButton.disabled = "disabled";
        endButton.disabled = "";
        websocket = initWebSocket(websocketEchoServerUri);
    }

    function stopDraw() {
        startButton.disabled = "";
        endButton.disabled = "disabled";
        websocket.close();
    }

    function initWebSocket(wsUri) {
        var ws = new WebSocket(wsUri);
        ws.onopen = onConnect;
        ws.onclose = onClose;
        ws.onerror = onError;
        ws.onmessage = updateChart;
        return ws;
    }

    toastr.options = {
      "closeButton": true,
      "debug": false,
      "newestOnTop": true,
      "progressBar": true,
      "positionClass": "toast-top-center",
      "preventDuplicates": false,
      "onclick": null,
      "showDuration": "300",
      "hideDuration": "1000",
      "timeOut": "10000",
      "extendedTimeOut": "1000",
      "showEasing": "swing",
      "hideEasing": "linear",
      "showMethod": "slideDown",
      "hideMethod": "slideUp"
    }
   
    function updateChart(wsEvent) {
        var newData = JSON.parse(wsEvent.data);
        
        if ("PnLRecords" in newData) {
        	chartData.splice(0,chartData.length);
        	chartData.push.apply(chartData, newData.PnLRecords);
        }

        if ("TradeRecords" in newData) {
            var tradeRecords = newData.TradeRecords;

            if (tradeRecords.length > 0) {
                tradeRecordTable.clear();
                stockEvents.splice(0,stockEvents.length);

                for (var j = 0; j < tradeRecords.length; j++) {
                    var currentRecord = tradeRecords[j];
                    var currentLine = [
                        currentRecord.CreateDate,
                        currentRecord.FilledDate,
                        currentRecord.Symbol,
                        currentRecord.Side == "BID" ? "<div><span class=\"label label-danger\">买</span></div>" : "<div><span class=\"label label-success\">卖</span></div>",
                        currentRecord.Quantity,
                        currentRecord.Price,
                        currentRecord.CommissionCost,
                        currentRecord.StampDutyCost
                    ];

                    var a = currentRecord.FilledDate.split(/[^0-9]/);

                    var newEvent = {};
                    newEvent.date = moment.utc(currentRecord.FilledDate, "YYYY-MM-DD HH:mm:ss").format("YYYY-MM-DDTHH:mm:ssZ");
                    newEvent.graph = "g1";

                    if (currentRecord.Side == "BID") {
                        newEvent.text = "买";
                        newEvent.backgroundColor = "#f45342";
                        newEvent.type = "arrowUp";
                        newEvent.description = "买[" + currentRecord.Symbol + "] " + currentRecord.Quantity + "股";
                    } else {
                        newEvent.text = "卖";
                        newEvent.backgroundColor = "#11c62d";
                        newEvent.type = "arrowDown";
                        newEvent.description = "卖[" + currentRecord.Symbol + "] " + currentRecord.Quantity + "股";
                    }

                    stockEvents.push(newEvent);

                    tradeRecordTable.row.add(currentLine);
                }

                tradeRecordTable.draw();
            }
        }

        chart.validateData();
        chart.periodSelector.setDefaultPeriod();

        if ("status" in newData && newData.status == "stopped") {
            toastr["success"]("策略实例运行完毕").css("width","700px");
            stopDraw();
            startButton.disabled = "disabled";
            return;
        }

    }

    function  onmessage(wsEvent) {
        console.log(wsEvent.data);
    };

    function onConnect(wsEvent) {
        console.log("Server connection successful. Listening for data now.");
        websocket.send("start");
        interval = setInterval(getDataFromServer, 300);
    }

    function onError(wsEvent) {
        console.log("ERROR:" + wsEvent);
    }

    function onClose(wsEvent) {
        console.log("Server connection closed");
        websocket.send("stop");
        clearInterval(interval);
    }


    function getDataFromServer() {
        websocket.send("update");
    }


});