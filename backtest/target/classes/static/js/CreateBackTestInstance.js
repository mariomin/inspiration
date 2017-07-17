$(function() {


// create the editor
var container = document.getElementById('param-editor');
var symbolContainer = document.getElementById('symbol-editor');
var options = {
    mode: 'tree',
    modes: ['tree', 'code'], // allowed modes
    onError: function (err) {
      alert(err.toString());
    },
    onModeChange: function (newMode, oldMode) {
      console.log('Mode switched from', oldMode, 'to', newMode);
    }
};
var editor = new JSONEditor(container, options);
var symbolEdtior = new JSONEditor(symbolContainer, options);

symbolEdtior.set({"600827.SH":1000});

var json = {
    "Universe": [
      {
        "Symbol": "600827.SH",
        "InitPosition": "1000",
        "BuySecurityAccount": "buySC001",
        "SellSecurityAccount": "sellSC001",
        "BuyTradingAccount": "buyTC001",
        "SellTradingAccount": "sellTC001",
        "PortfolioNo": "pf001",
        "PortfolioType": "pftype01"
      }
    ],
    "Breakout_PressureRatePeriod":15,
    "Breakout_BoundaryPointN1":30,
    "Breakout_BoundaryPointN2":60,
    "Breakout_BreakPointR":0.015,
    "Breakout_BreakPointP":0.5,
    "Breakout_WaveNum":5,
    "Breakout_LowBoundary1":0.005,
    "Breakout_QtyStandardThreshold":1,
    "Breakout_ParaRate":0.5,
    "Breakout_MinPressureRate":4,
    "Breakout_AskValueThreshold":150000,
    "Breakout_LowBoundary2":0.003,
    "Breakout_PressureUpdatePara":0.003,
    "Breakout_SignalParaP1":10,
    "Breakout_SignalParaP2":0,
    "Breakout_SignalParaP3":15,
    "Breakout_SignalParaP4":150000,
    "Breakout_SignalParaP5":0.5,
    "Breakout_SignalParaP6":2,
    "Breakout_SignalParaP7":0.003,
    "Breakout_SignalParaP8":50000,
    "Breakout_SignalParaP9":4,
    "Breakout_SignalParaP10":0.09,
    "Breakout_AvgPriceLag":10,
    "Breakout_EMAPressureRatioLag":10,
    "Breakout_CloseMaxDrop":0.003,
    "BuyLevel":1,
    "BuyDeviation":0.01,
    "SellLevel":1,
    "SellDeviation":-0.01,
    "CloseOpenParaP1":2,
    "CloseOpenParaP2":500000,
    "CloseOpenParaP3":0.2,
    "CloseOpenParaP4":4,
    "CloseOpenParaP5":3,
    "AutoTrade":true
};
editor.set(json);

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


$("#createBtn").click(function(){
    $("#createBtn").text("创建中......");
    $("#createBtn").prop('disabled', true);


    var insId = "strategy:" + moment(new Date(),"YYYY-MM-DD HH:mm:ss.SSS").utcOffset(0, true).format();

    var instanceParam = {};
    instanceParam['RequestId'] = insId;


    var formArray = $('#create-instance-form').serializeArray();
    for (var i = 0; i < formArray.length; i++){
        instanceParam[formArray[i]['name']] = formArray[i]['value'];
    }

    instanceParam['BenchMarks'] = $('#select-benchmark').val();
    instanceParam['StrategyParam'] = editor.get();
    instanceParam['Universe'] = symbolEdtior.get();


    $.ajax({
      url: '/instance/create',
      contentType: 'application/json; charset=utf-8',
      type: 'POST',
      data: JSON.stringify(instanceParam)
    })
    .done(function(msg) {
      if (msg.result == "success") {
        $("#createBtn").text("创建成功");
        toastr["success"](msg.strategy + " 策略实例创建成功( ID=" + msg.id + " )！").css("width","700px");
        $("#createBtn").prop('disabled', false);
        $("#createBtn").text("创建回测实例");
      } else {
        $("#createBtn").text("创建");
        $("#createBtn").prop('disabled', false);
        toastr["error"]("策略实例创建失败！");
      }
    });
});

var dtPickerOption = {
  locale:"zh-cn",
  sideBySide:true,
  format:"YYYY-MM-DD HH:mm:ss"
}


$('#input-starttime').datetimepicker(dtPickerOption);
$('#input-endtime').datetimepicker(dtPickerOption);



//END
});