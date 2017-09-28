<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控商户订单成功率趋势图</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/eCharts/echarts.js"></script>
</head>
<body>
	<div class="control-group">
		<div class="controls">
			<div style="text-align:center;padding-top: 20px;font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;">风控商户订单成功率趋势图</div>
		</div>
		<li hidden><a id="url_id" href="${ctx}/risk/riskMerchantOrderConversionRatio/"></a></li>
	</div>
	<div class="control-group">
		<div class="controls" align="left" style="padding-top: 20px;width:20%;">

			<div style="width:1000px;">
				<%--<label >开始时间：</label>--%>
				<input id="beginStatisticsTime" name="beginStatisticsTime" type="hidden" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${statisticsRecord.beginStatisticsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					<%-----%>
 				<input id="endStatisticsTime" name="endStatisticsTime" type="hidden" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${statisticsRecord.endStatisticsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					<label >报表类型：</label>
					<select class="input-xlarge" id="check_date_id" name="checkDate" style="position:relative;bottom:4px;">
						<option value="day" selected="selected">当日</option>
						<option value="week" >最近七天</option>
						<option value="theMonth" >本月</option>
						<%--<option value="preMonth" >上月</option>--%>
						<%--<option value="year" >本年</option>--%>
					</select>
					<label >商户编号：</label> <input type="text" id="merchantId" maxlength="20" />
					<%--<label >产品编号：</label> <input type="text" id="productCode" maxlength="20" />--%>
					<label>产品名称：</label>
						<select id="productCode" class="input-xlarge">
							<option value="" label="请选择"/>
							<c:forEach items="${productTypeList}" var="wStatus">
								<option value="${wStatus.value}"> ${wStatus.content} </option>
							</c:forEach>
						</select>
					<label >主机名称：</label> <input type="text" id="hostName" maxlength="20" />
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
            var checkText = $("#check_date_id :checked").text();
            var today = getDateStr(0);
			$("#beginStatisticsTime").val(today);
			$("#endStatisticsTime").val(today);
            //默认查询总数和总金额
            onSubmit();
		 	//选择昨天、当月等
			$("#check_date_id").change(function(){
				var checkText = $("#check_date_id :checked").text();
				var today = getDateStr(0);
				if(checkText == '当日'){
					$("#beginStatisticsTime").val(today);
					$("#endStatisticsTime").val(today);
				}else if(checkText == '最近七天'){
					var theDayOfLast7Days = getDateStr(-6);
					$("#beginStatisticsTime").val(theDayOfLast7Days);
					$("#endStatisticsTime").val(today);
				}else if(checkText == '本月'){
					var startMonth = getMonthStartDate();
					$("#beginStatisticsTime").val(startMonth);
					$("#endStatisticsTime").val(today);
				}
//				else if(checkText == '本年'){
//					var year = getYearStr(0);
//					$("#beginStatisticsTime").val(year + "-01-01");
//					$("#endStatisticsTime").val(today);
//				}else if(checkText == '上月'){
//					var startMonth = getPreMonthStartDate();
//					var endMonth = getPreMonthEndDate();
//					$("#beginStatisticsTime").val(startMonth);
//					$("#endStatisticsTime").val(endMonth);
//				}else if(checkText == '上年'){
//					var year = getYearStr(-1);
//					$("#beginStatisticsTime").val(year + "-01-01");
//					$("#endStatisticsTime").val(year + "-12-31");
//				}
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
			var beginStatisticsTime = $("#beginStatisticsTime").val();
			var endStatisticsTime = $("#endStatisticsTime").val();
			var merchantId = $("#merchantId").val();
            var productCode = $("#productCode").val();
            var hostName = $("#hostName").val();

			var url = $("#url_id").attr("href")+"getStatisticsInfo";
			$.post($("#url_id").attr("href")+"getStatisticsInfo",{
				checkDate : checkDate,
				beginStatisticsTime:beginStatisticsTime,
				endStatisticsTime:endStatisticsTime,
                merchantId:merchantId,
                productCode:productCode,
                hostName:hostName
				},function(data){
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('statistical_id'));
                option = {
                    title : {
                        text: '商户订单成功率',
                        subtext: '来源于实时数据'
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:[]
                    },
                    toolbox: {
                        show : true,
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
                            boundaryGap : false,
                            data : []
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel : {
                                formatter: '{value} %'
                            }
                        }
                    ],
                    series : [
                        {
                            name:'成功率',
                            type:'line',
                            data:[]
                        }
                    ]
                };

                var legendArr = [];//图标标题数组(类型)
                var xAxisArr = [];//X轴的显示
                var seriesArr = [];//数据显示

                for (var i = 0; i < data.length; i++) {
                    if(data[i].day ==null || data[i].day== undefined){
                        continue;
                    }
                    xAxisArr.push(data[i].day);
                    seriesArr.push(data[i].sucessRatio * 100);
                }
                legendArr.push("成功率");
                option.xAxis[0].data = xAxisArr;
                option.legend.data = legendArr;
                option.series[0].data = seriesArr;

                // 为echarts对象加载数据
                myChart.setOption(option);
            })

		}

    </script>
</body>
</html>