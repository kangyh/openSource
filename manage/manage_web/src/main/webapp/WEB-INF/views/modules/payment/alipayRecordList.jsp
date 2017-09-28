<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>支付宝扫码支付管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/alipayRecord/">支付宝扫码支付列表</a></li>
		<shiro:hasPermission name="payment:alipayRecord:edit"><li><a href="${ctx}/payment/alipayRecord/form">支付宝扫码支付添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="alipayRecord" action="${ctx}/payment/alipayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input path="alipayId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="outTradeNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>交易状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('WeChatRecordStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>交易时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${alipayRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${alipayRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易订单号</th>
				<th>商户编码</th>
				<th>商户订单号</th>
				<th>交易状态</th>
				<th>总金额</th>
				<th>成功金额</th>
				<th>手续费金额</th>
				<th>交易时间</th>
				<th>货币类型</th>
				<shiro:hasPermission name="payment:alipayRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="alipayRecord">
			<tr>
				<td><a href="${ctx}/payment/alipayRecord/form?id=${alipayRecord.id}">
					${alipayRecord.alipayId}
				</a></td>
				<td>
					${alipayRecord.merchantId}
				</td>
				<td>
					${alipayRecord.outTradeNo}
				</td>
				<td>
					${fns:getDictLabel(alipayRecord.status, 'WeChatRecordStatus', '')}
				</td>
				<td>
					${alipayRecord.totalAmount}
				</td>
				<td>
					${alipayRecord.successAmount}
				</td>
				<td>
					${alipayRecord.feeAmount}
				</td>
				<td>
					<fmt:formatDate value="${alipayRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${alipayRecord.currency}
				</td>
				<shiro:hasPermission name="payment:alipayRecord:edit"><td>
    				<a href="${ctx}/payment/alipayRecord/form?id=${alipayRecord.id}">修改</a>
					<a href="${ctx}/payment/alipayRecord/delete?id=${alipayRecord.id}" onclick="return confirmx('确认要删除该支付宝扫码支付吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>