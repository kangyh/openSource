<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运营统计报表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var groupValue = $("#groupTypeValue").val();
			$(":checkbox[name='groupType']").each(function(i,v){
				var value = v.value;
				var groups = groupValue.split(",");
				var ischecked = false;
				for(var i=0;i<groups.length;i++){
					if(groups[i]==value){
						ischecked = true;
					}
				}
				if(ischecked){
					$(v).attr("checked","checked");	
				}else{
					$(v).removeAttr("checked");
				}
			});
			

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
				var operator = $("#operator").val();
				var bankType = $("#bankType").val();
				var beginStatisticsTime = $("#beginStatisticsTime").val();
				var endStatisticsTime = $("#endStatisticsTime").val();
				//var groupType = $("input[name='groupType']:checked").val();
				var groupType = "";
				$("input[name='groupType']:checked").each(function(){
					if(groupType==""){
						groupType = $(this).val();
					}else{
						groupType = groupType+","+$(this).val();
					}
				});
				var host = "${ctx}/payment/statisticsOperationRecord/exportExcel";
				var url = host+"?merchantId="+merchantId+"&merchantCompany="+merchantCompany+"&allowSystem="+allowSystem+"&transType="+transType+"&productCode="+productCode+
						"&payType="+payType+"&channelPartners="+channelPartners+"&bankCode="+bankCode+"&operator="+operator+"&bankType="+bankType+
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
			
			$(":checkbox[name='groupType']").each(function(i,v){
				$(v).removeAttr("checked");
			});
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
	.form-search .ul-form li label{
		width:85px;
	}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/statisticsOperationRecord/">财务统计报表列表</a></li>
		<shiro:hasPermission name="payment:statisticsRecord:edit"><li><a href="${ctx}/payment/statisticsOperationRecord/form">财务统计报表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="statisticsRecord" action="${ctx}/payment/statisticsOperationRecord/" method="post" class="breadcrumb form-search">
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
			<li><label>维系员工：</label>
				<form:input path="operator" htmlEscape="false" maxlength="50" class="input-medium width_input"/>
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
				<label>查看类型：</label>
				<label><input name="groupType"  type="checkbox" value="merchant">按商户</label>
				<label><input name="groupType" type="checkbox" value="channel">按通道</label>
				<label ><input name="groupType" type="checkbox" value="bank">银行名称</label>
				<label><input name="groupType" type="checkbox" value="bankCardType">卡类型</label>
				<label><input name="groupType"  type="checkbox" value="transType">交易类型</label>
				<label><input name="groupType" type="checkbox" value="product">产品名称</label>
				<label ><input name="groupType" type="checkbox" value="payType">支付类型</label>
				<label><input name="groupType" type="checkbox" value="operator">维系员工</label>
			</li>
			<li class="btns" style="margin-left:35px;">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
				<input id="btnClear" class="btn btn-primary" onclick="onClear()" type="button" value="清空"/>
				<input id="exportExcel" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>

	<table id="timeTable" class="table table-striped table-bordered table-condensed"  >
		<thead>
			<tr>
				<c:forEach items="${headers}" var="item">
					<c:if test="${item!=null && item!=''}">
						<th style="text-align: center">${fns:getDictLabel(item, 'OperationHeaders', '')}</th>
					</c:if> 
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="lists">
				<tr>
		 		 	<c:forEach items="${headers}" var="headList">
		 		 		
							<c:choose>
							
					 		 	<c:when test="${headList=='channelPartners' }">
					 		 		<td style="text-align: center">
										${fns:getDictLabel(lists[headList], 'ChannelPartner', '')}
								</c:when>
					 		 	<c:when test="${headList=='channelType' }">
					 		 		<td style="text-align: center">
										${fns:getDictLabel(lists[headList], 'ChannelType', '')}
								</c:when>
					 		 	<c:when test="${headList=='bankType' }">
					 		 		<td style="text-align: center">
										${fns:getDictLabel(lists[headList], 'BankcardType', '')}
								</c:when>
					 		 	<c:when test="${headList=='transType' }">
					 		 		<td style="text-align: center">
										${fns:getDictLabel(lists[headList], 'TransType', '')}
								</c:when>
					 		 	<c:when test="${headList=='productCode' }">
					 		 		<td style="text-align: center">
										${fns:getDictLabel(lists[headList], 'ProductCodeType', '')}
								</c:when>
					 		 	<c:when test="${headList=='payType' }">
					 		 		<td style="text-align: center">
										${fns:getDictLabel(lists[headList], 'payType', '')}
								</c:when>
					 		 	<c:when test="${headList=='sucessAmount' }">
					 		 		<td style="text-align: right">
										<fmt:formatNumber value="${lists[headList]}" pattern="￥#,##0.00" /> 
								</c:when>
					 		 	<c:when test="${headList=='feeAmount' }">
					 		 		<td style="text-align: right">
										<fmt:formatNumber value="${lists[headList]}" pattern="￥#,##0.00" /> 
								</c:when>
					 		 	<c:when test="${headList=='settleAmount' }">
					 		 		<td style="text-align: right">
										<fmt:formatNumber value="${lists[headList]}" pattern="￥#,##0.00" /> 
								</c:when>
								<c:otherwise>
								<td style="text-align: center">
									${lists[headList]}
								</c:otherwise>
							
							</c:choose>
						
						</td>
					</c:forEach> 
					
				</tr>
			</c:forEach>
			<tr>
				<td style="text-align: center">合计</td>
				<c:forEach begin="1" end="${size}" var="i">
					<td style="text-align: center">--</td>
				</c:forEach>
				<td style="text-align: center">${sucessCount }</td>
				<td style="text-align: right"><fmt:formatNumber value="${sucessAmount}" pattern="￥#,##0.00" /> </td>
				<td style="text-align: right"><fmt:formatNumber value="${feeAmount}" pattern="￥#,##0.00" /> </td>
				<td style="text-align: right"><fmt:formatNumber value="${settleAmount}" pattern="￥#,##0.00" /> </td>
			</tr>
		</tbody>
	</table>
	
	
	<div class="pagination">${page}</div>
</body>
</html>