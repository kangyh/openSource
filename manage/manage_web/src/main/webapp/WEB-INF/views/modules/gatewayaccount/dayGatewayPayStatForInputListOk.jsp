<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网关支付核账管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			//汇总
			$("#isHz").bind("click", function(){
				if($(this).attr("checked")){
					$("#hz").val("checked");
				}else{
					$("#hz").val("");
				}
				//执行查询
				$("#searchForm").submit();
			});
			
			$("#searchForm").submit(function(){
				var bgTime = $("#beginTime").val();
				var endTime = $("#endTime").val();
				if (bgTime == "" && endTime != "" || bgTime != ""
						&& endTime == "") {
					alert("统计的起始时间不允许为空!");
					return false;
				}
				if (bgTime != "" && endTime != "") {
					var d1 = new Date(bgTime.replace(/\-/g, "\/"));
					var d2 = new Date(endTime.replace(/\-/g, "\/"));
					if (d1 > d2) {
						alert("起始日期不能大于结束日期!");
						return false;
					}
				}
			});
			
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/gatewayAccount/dayGatewayPayStatForInputOk">已录入的网关支付核账列表</a></li>
	</ul>
	<form id="searchForm" modelAttribute="dayGatewayPayStatForInput" action="${ctx}/gatewayAccount/dayGatewayPayStatForInputOk" 
		method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="hz" name="hz" type="hidden" />
		<input id="channelCodeHidden" name="channelCodeHidden" type="hidden" value="${channelCodeHidden }" />
		<ul class="ul-form">
			<li><label>统计时间：</label>
				<input id="beginTime" name="beginTime" type="text" style="width:115px;" maxlength="20" class="input-medium Wdate"
					value="${dayGatewayPayStatForInput.beginTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endTime" name="endTime" type="text" style="width:115px;" maxlength="20" class="input-medium Wdate"
					value="${dayGatewayPayStatForInput.endTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>通道合作方：</label>
				<select name="channelCode" id="channelCode" style="width: 230px;">
					<option value="">-通道合作方-</option>
					<c:forEach var="item" items="${channelCodeList}" varStatus="status">
							<c:choose>
								<c:when test="${item.channelPartners == 'DIRCON'}">
									 <c:set var="selChannelCode" value="${item.channelPartners}_${item.bankCode}"></c:set> 
									 <c:if test="${channelCode == selChannelCode}">
										<option value="${item.channelPartners}_${item.bankCode}"  selected>${item.channelPartnersName }_${item.bankName }</option>
									 </c:if>  
									 <c:if test="${channelCode != selChannelCode}">
										<option value="${item.channelPartners}_${item.bankCode}">${item.channelPartnersName }_${item.bankName }</option>
									 </c:if>  
								</c:when>
								<c:otherwise>
									 <c:if test="${channelCode == item.channelPartners}">
										<option value="${item.channelPartners}"  selected>${item.channelPartnersName }</option>
									 </c:if>  
									 <c:if test="${channelCode != item.channelPartners}">
										<option value="${item.channelPartners}">${item.channelPartnersName }</option>
									 </c:if>  
								</c:otherwise>
							</c:choose>
					</c:forEach> 
				</select>
			</li>
			<li><label>
				<c:if test="${hz == null or hz == ''}">  
					<input type="checkbox" id="isHz" />
				</c:if>
				<c:if test="${hz == 'checked'}">
					<input type="checkbox" id="isHz" checked="checked"/>
				</c:if>
				是否汇总</label>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2">统计时间</th>
				<th rowspan="2">银行名称</th>
				<th rowspan="2">通道合作方</th>
				<th rowspan="2">成功笔数</th>
				<th rowspan="2">成功金额</th>
				<th rowspan="2">核对笔数</th>
				<th rowspan="2">核对金额</th>
				<th>差异笔数</th>
				<th>差异金额</th>
				<th rowspan="2">核对金额差异说明</th>
				<th rowspan="2">实际结算金额</th>
				<th>实际差异金额</th>
				<th rowspan="2">录入人</th>
				<th rowspan="2">录入时间</th>
				<th rowspan="2">实际资金差异说明</th>
				<th rowspan="2">操作</th>
			</tr>
			<tr>
				<th style="font-size: 11px;">成功笔数—核对笔数</th>
				<th style="font-size: 11px;">成功金额—核对金额</th>
				<th style="font-size: 11px;">核对金额—实际到账资金</th>
			</tr>
		</thead>
		<tbody id="bodyId">
		<c:choose>
			<c:when test="${empty page.list}">
				<tr>
					<td colspan="14">没有查到相关的数据</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${page.list}" var="dayGatewayPayStatForInput" varStatus="status">
					<tr>
						<td><fmt:formatDate value="${dayGatewayPayStatForInput.groupTime }" pattern="yyyy-MM-dd"/></td>
						<td>${dayGatewayPayStatForInput.bankName }</td>
						<td>${dayGatewayPayStatForInput.channelPartnersName }</td>
						<td>${dayGatewayPayStatForInput.successBillCount }</td>
						<td><fmt:formatNumber value="${dayGatewayPayStatForInput.successTradeAmt }" pattern="￥#,##0.00" /></td>
						<td>${dayGatewayPayStatForInput.inputCheckSuccessCount }</td>
						<td><fmt:formatNumber value="${dayGatewayPayStatForInput.inputCheckSuccessAmt }" pattern="￥#,##0.00" /></td>
						<td>${dayGatewayPayStatForInput.failCount }</td>
						<td><fmt:formatNumber value="${dayGatewayPayStatForInput.failAmount }" pattern="￥#,##0.00" /></td>
						<td>${dayGatewayPayStatForInput.inputNote }</td>
						<td><fmt:formatNumber value="${dayGatewayPayStatForInput.inputSettleAmt }" pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${dayGatewayPayStatForInput.realFailAmount }" pattern="￥#,##0.00" /></td>
						<td>${dayGatewayPayStatForInput.inputUser }</td>
						<td><fmt:formatDate value="${dayGatewayPayStatForInput.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${dayGatewayPayStatForInput.checkNote }</td>
						<td><a href="${ctx}/gatewayAccount/dayGatewayPayStatForInputOk/toUpdate?recordId=${dayGatewayPayStatForInput.recordId}">修改</a></td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		</tbody>
		<tfoot>
			<tr>
				<td>合计</td>
				<td>--</td>
				<td>--</td>
				<td id="successTotalSum">0</td>
				<td id="successTotalAmount">￥0.00</td>
				<td id="inputCheckSuccessTotalSum">0</td>
				<td id="inputCheckSuccessTotalAmount">￥0.00</td>
				<td id="failTotalSum">0</td>
				<td id="failTotalAmount">￥0.00</td>
				<td>--</td>
				<td id="inputSettleTotalAmount">￥0.00</td>
				<td id="realFailTotalAmount">￥0.00</td>
				<td>--</td>
				<td>--</td>
				<td>--</td>
				<td>--</td>
			</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
	$(function(){
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		var channelCode = $("#channelCodeHidden").val(); 
		$.ajax({
			type : "POST",
			url : "${ctx}/gatewayAccount/dayGatewayPayStatForInputOk/getStatisticsList",
			data : {
				"beginTime" : beginTime,
				"endTime" : endTime,
				"channelCode" : channelCode
			},
			dataType : "json",
			success : function(data){
				$("#successTotalSum").text(data.successTotalSum);
				$("#successTotalAmount").text(data.successTotalAmount);
				$("#inputCheckSuccessTotalSum").text(data.inputCheckSuccessTotalSum);
				$("#inputCheckSuccessTotalAmount").text(data.inputCheckSuccessTotalAmount);
				$("#failTotalSum").text(data.failTotalSum);
				$("#failTotalAmount").text(data.failTotalAmount);
				$("#inputSettleTotalAmount").text(data.inputSettleTotalAmount);
				$("#realFailTotalAmount").text(data.realFailTotalAmount);
			}
		});
		
	});
	</script>
</body>
</html>