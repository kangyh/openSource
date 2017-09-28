<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银联扫码支付管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		
		//导出
		function exportExcel(){
			if(checkDate()){
				var unionpayId = $("#unionpayId").val();
				var paymentId = $("#paymentId").val();
				var merchantId = $("#merchantId").val();
				var merchantOrderNo = $("#merchantOrderNo").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
				var type = $("#type").val();
				var status = $("#status").val();
				var groupType = $("input[name='groupType']:checked").val();
				var host = "${ctx}/payment/unionpayRecord/exportExcel";
				var url = host+"?unionpayId="+unionpayId+"&paymentId="+paymentId+"&merchantId="+merchantId +
						"&merchantOrderNo="+merchantOrderNo+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+
						"&type="+type+"&status="+status+"&groupType="+groupType;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/unionpayRecord/">银联扫码支付列表</a></li>
		<shiro:hasPermission name="payment:unionpayRecord:edit"><li><a href="${ctx}/payment/unionpayRecord/form">银联扫码支付添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="unionpayRecord" action="${ctx}/payment/unionpayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易号：</label>
				<form:input path="unionpayId" id="unionpayId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<%--<li><label>预下单号：</label>
				<form:input path="prepayId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>--%>
			<li><label>支付单号：</label>
				<form:input path="paymentId" id="paymentId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" id="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>交易类型：</label>
				<form:select path="type" id="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('UnionpayType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>交易状态：</label>
				<form:select path="status" id="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('UnionpayStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>时间：</label>
				<input name="beginCreateTime" id="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${unionpayRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateTime" id="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${unionpayRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
				<input name="groupType"  type="radio" value="1" style="margin-left: 10px;"  <c:if test="${groupType == 1}">checked="checked"</c:if> >按交易时间
				<input name="groupType" type="radio" value="2" style="margin-right: 10px;" <c:if test="${groupType == 2}">checked="checked"</c:if>>按成功时间
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易总金额</th>
				<th>失败总金额</th>
				<th>成功总金额</th>
				<th>手续费总金额</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td id="totalAmount">￥0.00</td>
				<td id="failedTotalAmount">￥0.00</td>
				<td id="successTotalAmount">￥0.00</td>
				<td id="feeTotalAmount">￥0.00</td>
			</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易号</th>
				<th>预下单号</th>
				<th>支付单号</th>
				<th>商户ID</th>
				<th>商户登录账号</th>
				<th>商户公司</th>
				<th>商户订单号</th>
				<th>来源方式</th>
				<th>交易时间</th>
				<th>交易金额</th>
				<th>交易类型</th>
				<th>交易状态</th>
				<th>手续费金额</th>
				<shiro:hasPermission name="payment:unionpayRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="unionpayRecord">
			<tr>
				<td><a href="${ctx}/payment/unionpayRecord/form?id=${unionpayRecord.unionpayId}">
					${unionpayRecord.unionpayId}
				</a></td>
				<td>
					${unionpayRecord.prepayId}
				</td>
				<td>
					${unionpayRecord.paymentId}
				</td>
				<td>
					${unionpayRecord.merchantId}
				</td>
				<td>
					${unionpayRecord.merchantLoginName}
				</td>
				<td>
					${unionpayRecord.merchantCompany}
				</td>
				<td>
					${unionpayRecord.merchantOrderNo}
				</td>
				<td>
					${fns:getDictLabel(unionpayRecord.requestType, 'RequestType', '')}
				</td>
				<td>
					<fmt:formatDate value="${unionpayRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatNumber value="${unionpayRecord.requestAmount}" pattern="￥0.00" />
				</td>
				<td>
					${fns:getDictLabel(unionpayRecord.type, 'UnionpayType', '')}
				</td>
				<td <c:if test="${unionpayRecord.status=='SUCCES'}"> style="background:${success_background}" </c:if>>
					${fns:getDictLabel(unionpayRecord.status, 'UnionpayStatus', '')}
				</td>
				<td>
					<fmt:formatNumber value="${unionpayRecord.feeAmount}" pattern="￥0.00" />
				</td>
				<shiro:hasPermission name="payment:unionpayRecord:edit"><td>
    				<a href="${ctx}/payment/unionpayRecord/form?id=${unionpayRecord.id}">修改</a>
					<a href="${ctx}/payment/unionpayRecord/delete?id=${unionpayRecord.id}" onclick="return confirmx('确认要删除该银联扫码支付吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			var unionpayId = $("#unionpayId").val();
			var paymentId = $("#paymentId").val();
			var merchantId = $("#merchantId").val();
			var merchantOrderNo = $("#merchantOrderNo").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			var type = $("#type").val();
			var status = $("#status").val();
			var groupType = $("input[name='groupType']:checked").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/payment/unionpayRecord/getStatisticsList",
				data : {
					"unionpayId" : unionpayId,
					"paymentId" : paymentId,
					"merchantId" : merchantId,
					"merchantOrderNo" : merchantOrderNo,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime,
					"type" : type,
					"status" : status,
					"groupType" : groupType
				},
				dataType : "json",
				success : function(data){
					$("#totalAmount").text(data.totalAmount);
					$("#failedTotalAmount").text(data.failedTotalAmount);
					$("#successTotalAmount").text(data.successTotalAmount);
					$("#feeTotalAmount").text(data.feeTotalAmount);
				}
			});
			
		});
	</script>
</body>
</html>