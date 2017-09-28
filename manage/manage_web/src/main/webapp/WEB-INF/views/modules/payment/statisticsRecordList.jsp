<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>财务统计报表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var groupValue = $("#groupTypeValue").val();
			$(":radio[name='groupType']").each(function(i,v){
				var value = v.value;
				if(groupValue==value){
					$(v).attr("checked","checked");	
					if("day"==groupValue || "month"==groupValue || groupValue=="year" ||"quarter"==groupValue){
						groupValue = 4;
					}
					$("table").each(function(i,val){
			
						if(i==groupValue){
							$(val).removeAttr("hidden");
						}else{
							$(val).attr("hidden","hidden");
						}
					})
				}else{
					$(v).removeAttr("checked");
				}
			});
			
			$(":radio[name='groupType']").change(function(){
				var value = $(":radio[name='groupType']:checked").val();
				//时段类型制为不选中
/* 				$(":radio[name='groupTime']").each(function(j,v){
					$(v).attr("checked","checked");
				}) 
				
				else if(i==4){
				$("#timeTable").removeAttr("hidden");
			}
				
				
				*/
				$("#searchForm").submit();
				
			});
			
			//选中时段类型，分组类型不选中
/* 			$(":radio[name='groupTime']").change(function(){
				var value = $(":radio[name='groupTime']:checked").val();
				
			 	$(":radio[name='groupType']").each(function(i,val){
					$(val).attr("checked","checked");
				}) 
			//	$("#searchForm").submit();
				
			}); */
			
			
			
			$("#btnSubmit").on("click",function(){
				$("#searchForm").submit();
			});
			
			
			
			//excel导出功能
		 	$("#exportExcel").on("click",function(){
				var merchantId = $("#merchantId").val();
				var merchantCompany = $("#merchantCompany").val();
				var allowSystem = $("#allowSystem").val();
				var transType = $("#transType").val();
				var productCode = $("#productCode").val();
				var payType = $("#payType").val();
				var channelPartners = $("#channelPartners").val();
				var bankCode = $("#bankCode").val();
				var bankName = $("#bankName").val();
				var bankType = $("#bankType").val();
				var beginStatisticsTime = $("#beginStatisticsTime").val();
				var endStatisticsTime = $("#endStatisticsTime").val();
				var groupType = $("input[name='groupType']:checked").val();
				
				var host = "${ctx}/payment/statisticsRecord/exportExcel";
				var url = host+"?merchantId="+merchantId+"&merchantCompany="+merchantCompany+"&allowSystem="+allowSystem+"&transType="+transType+"&productCode="+productCode+
						"&payType="+payType+"&channelPartners="+channelPartners+"&bankCode="+bankCode+"&bankName="+bankName+"&bankType="+bankType+
						"&beginStatisticsTime="+beginStatisticsTime+"&endStatisticsTime="+endStatisticsTime+"&groupType="+groupType;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
				
			});
			
			
		 	//选择昨天、当月等
			$("#statisticsDate").change(function(){

				var checkText = $("#statisticsDate :checked").text();
				var today = getDateStr(0);
				if(checkText == '全部'){
					$("#beginStatisticsTime").val('');
					$("#endStatisticsTime").val('');
				}else if(checkText == '昨天'){
					var yesterday = getDateStr(-1);
					$("#beginStatisticsTime").val(yesterday);
					$("#endStatisticsTime").val(yesterday);
				}else if(checkText == '本月'){
					var startMonth = getMonthStartDate();
					$("#beginStatisticsTime").val(startMonth);
					$("#endStatisticsTime").val(today);
				}else if(checkText == '本年'){
					var year = getYearStr(0);
					$("#beginStatisticsTime").val(year + "-01-01");
					$("#endStatisticsTime").val(today);
				}else if(checkText == '上月'){
					var startMonth = getPreMonthStartDate();
					var endMonth = getPreMonthEndDate();
					$("#beginStatisticsTime").val(startMonth);
					$("#endStatisticsTime").val(endMonth);
				}else if(checkText == '上年'){
					var year = getYearStr(-1);
					$("#beginStatisticsTime").val(year + "-01-01");
					$("#endStatisticsTime").val(year + "-12-31");
				}
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
 			//默认支付状态
			$("#statisticsDate").find("option").removeAttr("selected");
			$("#statisticsDate").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#statisticsDate option[selected]").text());
			//默认排序方式
			$("#allowSystem").find("option").removeAttr("selected");
			$("#allowSystem").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#allowSystem option[selected]").text());
			//所有银行
			$("#transType").find("option").removeAttr("selected");
			$("#transType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#transType option[selected]").text());
			
			$("#productCode").find("option").removeAttr("selected");
			$("#productCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#productCode option[selected]").text());

			$("#payType").find("option").removeAttr("selected");
			$("#payType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(4)").text($("#payType option[selected]").text());
			
			$("#channelPartners").find("option").removeAttr("selected");
			$("#channelPartners").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(5)").text($("#channelPartners option[selected]").text());
			
			$("#bankCode").find("option").removeAttr("selected");
			$("#bankCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(6)").text($("#bankCode option[selected]").text());

			$("#bankType").find("option").removeAttr("selected");
			$("#bankType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(7)").text($("#bankType option[selected]").text());
			
			$("#sortDirection").find("option").removeAttr("selected");
			$("#sortDirection").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(8)").text($("#sortDirection option[selected]").text());
			
			$("#sortType").find("option").removeAttr("selected");
			$("#sortType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(9)").text($("#sortType option[selected]").text());
			
		}
		
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
		
	</script>
	<style>
	.form-search .ul-form li{
		width:30%;
	}
	.width_input{
		width:150px;
	}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/statisticsRecord/">财务统计报表列表</a></li>
		<shiro:hasPermission name="payment:statisticsRecord:edit"><li><a href="${ctx}/payment/statisticsRecord/form">财务统计报表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="statisticsRecord" action="${ctx}/payment/statisticsRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form" style="min-width:1200px">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="19" class="input-medium width_input" />
			</li>
			<li><label>公司名称：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="20" class="input-medium width_input"/>
			</li>
			<li><label>商户来源：</label>
				<form:select path="allowSystem" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('AllowSystemSource')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>交易类型：</label>
				<form:select path="transType" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('TransType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>产品类型：</label>
				<form:select path="productCode" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('ProductCodeType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>支付类型：</label>
				<form:select path="payType" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('payType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>通道合作方：</label>
				<form:select path="channelPartners" class="input-medium">
					<form:option value="R1" label="全部"/>
					<%-- <form:options items="${fns:getDictList('payType')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
					 <form:options items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				</form:select>
			</li>
			<li><label>银行名称：</label>
				<%-- <form:input path="bankName" htmlEscape="false" maxlength="64" class="input-medium"/> --%>
				<form:select id="bankCode" path="bankCode" name="bankCode" class="input-medium" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
		    	    <form:option value="R1" label="全部"/>
				    <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
			    </form:select>
			</li>
			<li><label>银行名称：</label>
				<form:input path="bankName" htmlEscape="false" maxlength="50" class="input-medium width_input"/>
			</li>
			<li><label>银行卡类型：</label>
				<form:select id="bankType" path="bankType" name="bankType" class="input-medium" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
		    	  <form:option value="R1" label="全部"/>
				  <form:options items="${fns:getDictList('BankcardType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			</li>
			
			<li><label>升降排序：</label>
				<form:select id="sortDirection" path="sortDirection" name="sortDirection" class="input-medium" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('sortDirection')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>排序类型：</label>
				<form:select id="sortType" path="sortType" name="sortType" class="input-medium" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('sortType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li style="width:500px;"><label>成功时间：</label>
				<input id="beginStatisticsTime" name="beginStatisticsTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${statisticsRecord.beginStatisticsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endStatisticsTime" name="endStatisticsTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${statisticsRecord.endStatisticsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					
				<form:select id="statisticsDate" path="statisticsDate" name="statisticsDate" class="input-mini" >
 					<form:option value="R1" label="全部"/> 
					<form:options items="${fns:getDictList('statisticsDate')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<input id="groupTypeValue" type="hidden" value="${groupType}" >
			</li>
			<li style="width:80%">
				<label>分组类型：</label>
				<label><input name="groupType"  type="radio" value="3">按商户</label>
				<label><input name="groupType" type="radio" value="2">按交易类型</label>
				<label style="width:145px;"><input name="groupType" type="radio" value="1">按商户+交易类型</label>
				<label><input name="groupType" type="radio" value="0">按通道</label>
				<label><input name="groupType"  type="radio" value="day">按天</label>
				<label><input name="groupType" type="radio" value="month">按月</label>
				<label style="width:145px;"><input name="groupType" type="radio" value="quarter">按季度</label>
				<label><input name="groupType" type="radio" value="year">按年</label>
			</li>
	<!-- 		<li style="width:80%">
				<label>时段类型：</label>
				<label><input name="groupTime"  type="radio" value="day">按天</label>
				<label><input name="groupTime" type="radio" value="month">按月</label>
				<label style="width:145px;"><input name="groupTime" type="radio" value="quarter">按季度</label>
				<label><input name="groupTime" type="radio" value="year">按年</label>
			</li> -->
			<li class="btns" style="margin-left:35px;">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
				<input id="btnClear" class="btn btn-primary" onclick="onClear()" type="button" value="清空"/>
				<input id="exportExcel" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>

	 <table id="channelTable" class="table table-striped table-bordered table-condensed" hidden="hidden">
		<thead>
			<tr>
				<th>交易类型</th>
				<th>通道提供者</th>
				<th>银行名称</th>
				<th>通道编码</th>
				<th>交易笔数(笔)</th>
				<th>成功交易金额(元)</th>
				<th>手续费(元)</th>
				<th>结算到商户金额(元)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="statisticsRecord">
			<tr>
				<td>
					${fns:getDictLabel(statisticsRecord.transType, 'TransType', '')}
				</td>
				<td>
					${fns:getDictLabel(statisticsRecord.channelPartners, 'ChannelPartner', '')}
				</td>
				<td>
					${statisticsRecord.bankName}
				</td>
				<td>
					${statisticsRecord.channelCode}
				</td>
				<td>
					${statisticsRecord.sucessCount}
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${statisticsRecord.sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsRecord.feeAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsRecord.settleAmount}" pattern="￥#,##0.00" /> 
				</td>
			</tr>
		</c:forEach>
		<tr>
			
				<td>
					总计
				</td>
				<td>
					--
				</td>
				<td>
					--
				</td>
				<td>
					--
				</td>
				<td>
					${sucessCount}
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${feeAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${settleAmount}" pattern="￥#,##0.00" /> 
				</td>
		</tr>
		</tbody>
	</table>
	
	
	<table id="merchantTransTable" class="table table-striped table-bordered table-condensed" hidden="hidden">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司</th>
				<th>交易类型</th>
				<th>交易笔数(笔)</th>
				<th>成功交易金额(元)</th>
				<th>手续费(元)</th>
				<th>结算金额(元)</th>
				<shiro:hasPermission name="payment:statisticsRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="statisticsRecord">
			<tr>
				<td>
					${statisticsRecord.merchantId}
				</td>
				<td>
					${statisticsRecord.merchantLoginName}
				</td>
				<td>
					${statisticsRecord.merchantCompany}
				</td>
				<td>
					${fns:getDictLabel(statisticsRecord.transType, 'TransType', '')}
				</td>
			
				<td>
					${statisticsRecord.sucessCount}
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${statisticsRecord.sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsRecord.feeAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsRecord.settleAmount}" pattern="￥#,##0.00" /> 
				</td>
			</tr>
		</c:forEach>
		<tr>
			
				<td>
					总计
				</td>
				<td>
					--
				</td>
				<td>
					--
				</td>
				<td>
					--
				</td>
				<td>
					${sucessCount}
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${feeAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${settleAmount}" pattern="￥#,##0.00" /> 
				</td>
		</tr>
		
		</tbody>
	</table>
	
	
	<table id="transTable" class="table table-striped table-bordered table-condensed" hidden="hidden">
		<thead>
			<tr>
				<th>交易类型</th>
				<th>交易笔数(笔)</th>
				<th>成功交易金额(元)</th>
				<th>手续费(元)</th>
				<th>结算金额(元)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="statisticsRecord">
			<tr>
				
				<td>
					${fns:getDictLabel(statisticsRecord.transType, 'TransType', '')}
				</td>
			
				<td>
					${statisticsRecord.sucessCount}
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${statisticsRecord.sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsRecord.feeAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsRecord.settleAmount}" pattern="￥#,##0.00" /> 
				</td>
			</tr>
		</c:forEach>
		<tr>
			
				<td>
					总计
				</td>
				<td>
					${sucessCount}
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${feeAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${settleAmount}" pattern="￥#,##0.00" /> 
				</td>
		</tr>
		</tbody>
	</table>
	
	
	<table id="merchantTable" class="table table-striped table-bordered table-condensed" hidden="hidden">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司</th>
				<th>交易笔数(笔)</th>
				<th>成功交易金额(元)</th>
				<th>手续费(元)</th>
				<th>结算金额(元)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="statisticsRecord">
			<tr>
				<td>
					${statisticsRecord.merchantId}
				</td>
				<td>
					${statisticsRecord.merchantLoginName}
				</td>
				<td>
					${statisticsRecord.merchantCompany}
				</td>
				<td>
					${statisticsRecord.sucessCount}
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${statisticsRecord.sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsRecord.feeAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsRecord.settleAmount}" pattern="￥#,##0.00" /> 
				</td>
			</tr>
		</c:forEach>
		<tr>
			
				<td>
					总计
				</td>
				<td>
					--
				</td>
				<td>
					--
				</td>
				<td>
					${sucessCount}
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${feeAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${settleAmount}" pattern="￥#,##0.00" /> 
				</td>
		</tr>
		</tbody>
	</table> 
	
	<table id="timeTable" class="table table-striped table-bordered table-condensed"  hidden="hidden">
		<thead>
			<tr>
				<th style="text-align: center">${firstTitle}</th>
				<c:forEach items="${head}" var="item">
					<c:if test="${item!=null && item!=''}">
						<th style="text-align: center">${fns:getDictLabel(item, 'TransType', '')}</th>
					</c:if> 
				</c:forEach>
				<th style="text-align: center">合计</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${finaceLists}" var="lists">
				<tr>
					<td style="text-align: center">${lists['time']}</td>
		 		 	<c:forEach items="${head}" var="headList">
		 		 	<c:if test="${headList!=null }">
						<td style="text-align: right">
							<fmt:formatNumber value="${lists[headList] }" pattern="￥#,##0.00" />
						</td>
					</c:if>
					</c:forEach> 
					<td style="text-align: right">
						<fmt:formatNumber value="${lists['totalAmount']}" pattern="￥#,##0.00" />
					</td> 
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	
	<div class="pagination">${page}</div>
</body>
</html>