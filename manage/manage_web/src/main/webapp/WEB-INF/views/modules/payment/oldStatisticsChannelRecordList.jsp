<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>原始通道数据统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			Date.prototype.Format = function (fmt) { //author: meizz 
			    var o = {
			        "M+": this.getMonth() + 1, //月份 
			        "d+": this.getDate(), //日 
			        "h+": this.getHours(), //小时 
			        "m+": this.getMinutes(), //分 
			        "s+": this.getSeconds(), //秒 
			        "q+": Math.floor((this.getMonth() + 3) / 3), 
			        "S": this.getMilliseconds() //毫秒 
			    };
			    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			    for (var k in o)
			    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			    return fmt;
			}
			
			$("#btnSubmit").on("click",function(){

				var beginStatisticTime = $("#beginStatisticTime").val();
				var endStatisticTime = $("#endStatisticTime").val();
				var now = new Date().Format("yyyy-MM-dd");
				if(endStatisticTime<beginStatisticTime){
					alert("开始时间不能大于结束时间");
					return ;
				}
				if(now<endStatisticTime){
					alert("结束时间不能大于当前时间");
					return ;
				}
				
				$("#searchForm").submit();
			});
			
			
			$("#inTime").on("click",function(){
				window.location="${ctx}/payment/oldStatisticsChannelRecord/inTimeStatistics";
			});
			
			//excel导出功能
		 	$("#exportExcel").on("click",function(){
				var channelPartners = $("#channelPartners").val();
				var bankCode = $("#bankCode").val();
				var channelType = $("#channelType").val();
				var beginStatisticTime = $("#beginStatisticTime").val();
				var endStatisticTime = $("#endStatisticTime").val();
				
				var host = "${ctx}/payment/statisticsChannelRecord/exportExcel";
				var url = host+"?channelPartners="+channelPartners+"&bankCode="+bankCode+"&channelType="+channelType+"&beginStatisticTime="+beginStatisticTime+"&endStatisticTime="+endStatisticTime;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
				
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
 			//默认支付状态
			$("#channelPartners").find("option").removeAttr("selected");
			$("#channelPartners").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#channelPartners option[selected]").text());
			//默认排序方式
			$("#channelType").find("option").removeAttr("selected");
			$("#channelType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#channelType option[selected]").text());
			//所有银行
			$("#bankCode").find("option").removeAttr("selected");
			$("#bankCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#bankCode option[selected]").text());
			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/statisticsChannelRecord/">原始通道数据统计列表</a></li>
		<shiro:hasPermission name="payment:statisticsChannelRecord:edit"><li><a href="${ctx}/payment/oldStatisticsChannelRecord/form">通道数据统计添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="statisticsChannelRecord" action="${ctx}/payment/oldStatisticsChannelRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
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
			<li><label>支付通道类型：</label>
				<form:select path="channelType" class="input-medium">
					<form:option value="R1" label="全部"/>
					<%-- <form:options items="${fns:getDictList('PayType')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
					<form:options items="${fns:getEnumList('channelType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				</form:select>
			</li>
			<li><label>统计时间：</label>
				<input id="beginStatisticTime" name="beginStatisticTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${statisticsChannelRecord.beginStatisticTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endStatisticTime" name="endStatisticTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${statisticsChannelRecord.endStatisticTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
			<input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>通道合作方</th>
				<th>银行名称</th>
				<th>支付通道类型</th>
				<th>总笔数</th>
				<th>总金额（元）</th>
				<th>转化率</th>
				<th>成功总笔数</th>
				<th>成功总金额（元）</th>
				<th>成功率</th>
				<th>失败总笔数</th>
				<th>失败总金额（元）</th>
				<th>失败率</th>
				<th>统计日期(往日)</th>
				<th>统计时间(当日)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="statisticsChannelRecord">
			<tr>
				<td>
					${statisticsChannelRecord.channelPartnersName }
				</td>
				<td>
					${statisticsChannelRecord.bankName}
				</td>
				<td>
					${statisticsChannelRecord.channelTypeName }
				</td>
				<td>
					${statisticsChannelRecord.totalCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsChannelRecord.totalAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsChannelRecord.sucessCount/statisticsChannelRecord.totalCount}" pattern="0.00%" />
				</td>
				<td>
					${statisticsChannelRecord.sucessCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsChannelRecord.sucessAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsChannelRecord.sucessCount/((statisticsChannelRecord.sucessCount+statisticsChannelRecord.failCount) eq 0 ? 1:(statisticsChannelRecord.sucessCount+statisticsChannelRecord.failCount))}" pattern="0.00%" />
				</td>
				<td>
					${statisticsChannelRecord.failCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsChannelRecord.failAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsChannelRecord.failCount/((statisticsChannelRecord.sucessCount+statisticsChannelRecord.failCount) eq 0 ? 1:(statisticsChannelRecord.sucessCount+statisticsChannelRecord.failCount))}" pattern="0.00%" />
				</td>
				
				<td>
					<fmt:formatDate value="${statisticsChannelRecord.statisticTime}" type="both" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${statisticsChannelRecord.statisticHour}
				</td>
				
			</tr>
		</c:forEach>
		
		
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>