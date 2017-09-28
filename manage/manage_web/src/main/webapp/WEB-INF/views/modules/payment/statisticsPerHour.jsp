<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>财务统计趋势图</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/eCharts/echarts.js"></script>
</head>
<body>
	<div class="control-group">
		<div class="controls">
			<div style="text-align:center;padding-top: 20px;font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;">实时交易数据统计</div>
		</div>
		<li><a id="url_id" href="${ctx}/payment/statisticsRecordPerHour/"></a></li>
	</div>
	<div class="control-group">
		<div class="controls" align="left" style="padding-top: 20px;width:20%;">

			<div style="width:580px;"><label>开始时间：</label>
				<input id="beginStatisticsTime" name="beginStatisticsTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${statisticsRecord.beginStatisticsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
 				<input id="endStatisticsTime" name="endStatisticsTime" type="text"  readonly="true" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${statisticsRecord.endStatisticsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<select class="input-xlarge" id="check_date_id" name="checkDate" style="position:relative;bottom:4px;">
					<option value="day" selected="selected">当日</option>
					<option value="week" >最近七天</option>
					<option value="theMonth" >本月</option>
					<option value="preMonth" >上月</option>
					<option value="year" >本年</option>
					
				</select> 
				
				<form:form id="searchForm" modelAttribute="statisticsRecord"  method="post" class="breadcrumb form-search">
					<label>商户编码：</label>
					<form:input path="merchantId" htmlEscape="false" maxlength="19" class="input-medium width_input" />
					
					<label>通道合作方：</label>
					<form:select path="channelPartners" class="input-medium">
						<form:option value="R1" label="全部"/>
						<%-- <form:options items="${fns:getDictList('payType')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
						 <form:options items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
					</form:select>
   				</form:form>
			</div>
			
	
			<div style="padding-top: 20px;" align="center">
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 	<input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/>
				<input id="btnClear" class="btn btn-primary" type="button" onclick="back()" value="返回"/> -->
			</div>
		</div>
		<div class="controls" align="center" style="width:80%;margin-top:20px;">
			<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
		    <div id="statistical_id" style="width: 95%;height:550%;"></div>
		    <div id="statistical_id_text" style="width: 60%;height:600%;color: red;font-size: 2em;"></div>
		</div>
    </div>
    
    <script type="text/javascript">
     	
		$(document).ready(function() {
			//默认查询总数和总金额
			onSubmit();
			Array.prototype.contains = function ( needle ) {
				  for (i in this) {
				    if (this[i] == needle) return true;
				  }
				  return false;
			}
			
		 	//选择昨天、当月等
			$("#check_date_id").change(function(){

				var checkText = $("#check_date_id :checked").text();
				var today = getDateStr(0);
				if(checkText == '当日'){
					$("#beginStatisticsTime").val(today);
					$("#endStatisticsTime").val(today);
					$("#beginStatisticsTime").removeAttr("disabled");
					$("#endStatisticsTime").removeAttr("disabled");
				}else if(checkText == '最近七天'){
					var theDayOfLast7Days = getDateStr(-6);
					$("#beginStatisticsTime").val(theDayOfLast7Days);
					$("#endStatisticsTime").val(today);
					$("#beginStatisticsTime").attr("disabled","true");
					$("#endStatisticsTime").attr("disabled","true");
				}else if(checkText == '本月'){
					var startMonth = getMonthStartDate();
					$("#beginStatisticsTime").val(startMonth);
					$("#endStatisticsTime").val(today);
					$("#beginStatisticsTime").attr("disabled","true");
					$("#endStatisticsTime").attr("disabled","true");
				}else if(checkText == '本年'){
					var year = getYearStr(0);
					$("#beginStatisticsTime").val(year + "-01-01");
					$("#endStatisticsTime").val(today);
					$("#beginStatisticsTime").attr("disabled","true");
					$("#endStatisticsTime").attr("disabled","true");
				}else if(checkText == '上月'){
					var startMonth = getPreMonthStartDate();
					var endMonth = getPreMonthEndDate();
					$("#beginStatisticsTime").val(startMonth);
					$("#endStatisticsTime").val(endMonth);
					$("#beginStatisticsTime").attr("disabled","true");
					$("#endStatisticsTime").attr("disabled","true");
				}else if(checkText == '上年'){
					var year = getYearStr(-1);
					$("#beginStatisticsTime").val(year + "-01-01");
					$("#endStatisticsTime").val(year + "-12-31");
					$("#beginStatisticsTime").attr("disabled","true");
					$("#endStatisticsTime").attr("disabled","true");
				}
			});
			
		});	
		
		//获取当前时间
		function getNowFormatDate() {
		    var date = new Date();
		    var seperator1 = "-";
		    var seperator2 = ":";
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		            + " " + date.getHours() + seperator2 + date.getMinutes()
		            + seperator2 + date.getSeconds();
		    return currentdate;
		} 
		
		
		function getDateStr(addDayCount) {
			var dd = new Date();
			dd.setDate(dd.getDate() + addDayCount);//获取AddDayCount天后的日期
			var y = dd.getFullYear();
			var m = dd.getMonth() + 1;//获取当前月份的日期
			var d = dd.getDate();
			if(m<10){
				m="0"+m;
			}
			if(d<10){
				d="0"+d;
			}
			return y + "-" + m + "-" + d;
		}
		
		
		function getYearStr(addYearCount) {
			var dd = new Date();
			var y = dd.getFullYear()  + addYearCount;
			return y;
		}
		
		
		//获得本月的开始日期
		function getMonthStartDate(){
			var now = new Date(); //当前日期
			var nowMonth = now.getMonth(); //当前月
			var nowYear = now.getYear(); //当前年
			nowYear += (nowYear < 2000) ? 1900 : 0;
			var monthStartDate = new Date(nowYear, nowMonth, 1);
			return formatDate(monthStartDate);
		}

		//获得本月的结束日期
		function getMonthEndDate(){
			var now = new Date(); //当前日期
			var nowMonth = now.getMonth(); //当前月
			var nowYear = now.getYear(); //当前年
			nowYear += (nowYear < 2000) ? 1900 : 0;
			var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowYear, nowMonth));
			return formatDate(monthEndDate);
		}

		//获得上月开始时间
		function getPreMonthStartDate(){
			var now = new Date(); //当前日期
			var nowMonth = now.getMonth(); //当前月
			var nowYear = now.getYear(); //当前年
			nowYear += (nowYear < 2000) ? 1900 : 0;
			var lastMonthDate = new Date(); //上月日期
			lastMonthDate.setDate(1);
			lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
			var lastMonth = lastMonthDate.getMonth();
			var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
			return formatDate(lastMonthStartDate);
		}

		//获得上月结束时间
		function getPreMonthEndDate(){
			var now = new Date(); //当前日期
			var nowMonth = now.getMonth(); //当前月
			var nowYear = now.getYear(); //当前年
			nowYear += (nowYear < 2000) ? 1900 : 0;
			var lastMonthDate = new Date(); //上月日期
			lastMonthDate.setDate(1);
			lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
			var lastMonth = lastMonthDate.getMonth();
			var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(nowYear, lastMonth));
			return formatDate(lastMonthEndDate);
		}
		
		//获得某月的天数
		function getMonthDays(nowYear, myMonth){
			var monthStartDate = new Date(nowYear, myMonth, 1);
			var monthEndDate = new Date(nowYear, myMonth + 1, 1);
			var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);
			return days;
		} 
		
		//格式化日期：yyyy-MM-dd
		function formatDate(date) {
			var myyear = date.getFullYear();
			var mymonth = date.getMonth()+1;
			var myweekday = date.getDate();
	
			if(mymonth < 10){
				mymonth = "0" + mymonth;
			}
			if(myweekday < 10){
				myweekday = "0" + myweekday;
			}
			return (myyear+"-"+mymonth + "-" + myweekday);
		} 
		
		
		function back(){
			window.history.go(-1);
		}
	
		//查询
		function onSubmit(){
			var checkDate = $("#check_date_id").find("option:selected").val();
 			var merchantId = $("#merchantId").val();
			var channelPartners = $("#channelPartners").val(); 
			var beginStatisticsTime = $("#beginStatisticsTime").val();
			var endStatisticsTime = $("#endStatisticsTime").val();
			var url = $("#url_id").attr("href")+"getStatisticsInfo";
			$.post($("#url_id").attr("href")+"getStatisticsInfo",{
				checkDate : checkDate,
				beginStatisticsTime:beginStatisticsTime,
				endStatisticsTime:endStatisticsTime,
				merchantId:merchantId,
				channelPartners:channelPartners
				},function(data){
					 // 基于准备好的dom，初始化echarts实例
					 var myChart = echarts.init(document.getElementById('statistical_id'));
					
					//根据不同的类型显示不同的图标 
			    	var option = {
			   		    title : {
			   		    	text: '各通道的类型统计',
					        subtext: '数据来源：实时交易数据   单位：（元）'
			   		    },
			   		    tooltip : {
			   		        trigger: 'axis',
				  		     axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				  	        	type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				  	        } 
			   		    },
			   		    legend: {
			   		    	data:[]
			   		    },
			   		    toolbox: {
			   		        show : true,
			   		     	y: 'bottom',
			   		        feature : {
			   		        	mark : {show: true},
			   		        	dataView : {show: true, readOnly: false},
			   		            magicType : {show: true, type: ['line', 'bar']},
			   		            restore : {show: true},
			   		            saveAsImage : {show: true}
			   		        }
			   		    },
			   		    calculable : true,
			   		    xAxis : [
			   		        {
			   		            type : 'category',
			   		         	axisLabel:{
									interval:0,  //类目全显
									rotate:30    //旋转角度
				   	            },
			   		         	data : []
			   		        }
			   		    ],
			   		    yAxis : [
			   		        {
			   		            type : 'value'
			   		         	//position: 'right'
	//		   		         	splitNumber : 15,
			   		        }
			   		    ],
			   		    series : []
			   		};
					var legendArr = [];//图标标题数组(类型)
					var xAxisArr = [];//X轴的显示
					var seriesArr = [];//数据显示
					if(data != null && data != "" && data != undefined && data.length > 0){	
							legendArr.push("快捷");
							legendArr.push("快捷2");
							legendArr.push("网银");
							legendArr.push("代收");
							legendArr.push("退款");
							legendArr.push("代付");
							legendArr.push("转账");
							legendArr.push("微信支付");
							legendArr.push("支付宝扫码");
							legendArr.push("银联扫码支付");
							legendArr.push("线下汇款");
							legendArr.push("手续费");
							legendArr.push("结算金额");
					
					 var checkText = $("#check_date_id :checked").text();
					 for (var i = 0; i < data.length; i++) {
		            	
		                if(data[i].time ==null || data[i].time== undefined){
		                    	continue;
		                }
		                if(checkText == '当日'){
		                	xAxisArr.push(data[i].time+"时");
						}else if(checkText == '最近七天' || checkText == '本月' ||checkText == '上月'){
							xAxisArr.push(data[i].time+"日");
						}else if(checkText == '本年' || checkText == '上年'){
						   xAxisArr.push(data[i].time+"月");
						}
		                
		                   
		              }
					
						seriesArr = dayAssignment(data,xAxisArr,legendArr,seriesArr,checkDate);

	                    option.xAxis[0].data = xAxisArr;
	                    option.legend.data = legendArr;
	                    option.series = seriesArr;
	                    //单独的通道查询时，限制柱状图的宽度
	                    if(xAxisArr.length==1){
		                    option.barWidth = 70;
	                    }
	                    //过渡控制，隐藏loading（读取中）
	                    myChart.hideLoading();
	                    $("#statistical_id_text").text("");
					}else{
						option.series = seriesArr;
						$("#statistical_id_text").text("无数据！");
					}
                    // 为echarts对象加载数据

					myChart.setOption(option);
				}
			);
		}
	
		//实时交易赋值
		function dayAssignment(data,xAxisArr,legendArr,seriesArr,checkDate){
				//该类型的所有数据
				for(var i=0;i<legendArr.length;i++){
					var temp = {};
					var tempArray=[];
					temp.name = legendArr[i];
					
					if(i!=11 && i!=12){
						temp.type = 'bar';
						temp.stack='支付类型';
						temp.tooltip ={trigger: 'item'};
						for(var j = 0; j < xAxisArr.length; j++){
							var flag = true;
							for(var k=0;k<data[j].records.length;k++){
								if((getProductType(i)==data[j].records[k].payType)){
									tempArray.push(formatMoney(data[j].records[k].sucessAmount));
									flag = false;
								}
							}
							if(flag){
								tempArray.push("0.00");
							}
							
						}
					}else if(i==11){
						temp.type = 'bar';
						for(var j = 0; j < xAxisArr.length; j++){
							var feeAmount = 0;
							for(var k=0;k<data[j].records.length;k++){
								feeAmount = Number(feeAmount) +Number(data[j].records[k].feeAmount);
							}
							tempArray.push(formatMoney(feeAmount));
		
						}
						temp.color="#000000"
					}else{
						temp.type = 'line';
						for(var j = 0; j < xAxisArr.length; j++){
							var totalAmount = 0;
							for(var k=0;k<data[j].records.length;k++){
								totalAmount = Number(totalAmount) +Number(data[j].records[k].settleAmount);
								
							}
							tempArray.push(formatMoney(totalAmount));
		
						}
					}
					
					temp.data = tempArray;
					temp.markPoint = {};
					/*		temp.markPoint.data = [{type : 'max', name: '最大值'},{type : 'min', name: '最小值'}];
			 		temp.markLine = {};
					temp.markLine.data = [{type : 'average', name: '平均值'}]; */
					seriesArr.push(temp);
				}
			return seriesArr;
		}
		
		function getProductType(i){
			if(i==0){
				return 'QUICKP';
			}else if(i==1){
				return 'QUICK2';
			}else if(i==2){
				return 'GATEWY'
			}else if(i==3){
				return 'BATCOL'
			}else if(i==4){
				return 'REFUND'
			}else if(i==5){
				return 'BATPAY'
			}else if(i==6){
				return 'TRAFER'
			}else if(i==7){
				return 'WECHAT'
			}else if(i==8){
				return 'ALIPAY'
			}else if(i==9){
				return 'UPCODE'
			}else if(i==10){
				return 'REMITS'
			}
		}
		
		function formatMoney(money){
			money = money+"";
			if(money==null||money==""||money==undefined){
				return "0.00";
			}
			if(money.indexOf(".")==-1){
				return money+".00";
			}
			if(money.split(".")[1].length==1){
				return money+"0";
			}
			return money.substring(0,money.indexOf(".")+3);
		}
		
	
		
		//清空
		function onClear(){
			
			$("#check_date_id").find("option").removeAttr("selected");
			$("#check_date_id").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#check_date_id option[selected]").text());
			
			$("#check_remark_id").find("option").removeAttr("selected");
			$("#check_remark_id").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#check_remark_id option[selected]").text());
			
			$("#channelCode").find("option").removeAttr("selected");
			$("#channelCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#channelCode option[selected]").text());
		}
     	
	
		
    </script>
</body>
</html>