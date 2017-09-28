<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银联扫码预下单管理</title>
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
		<li class="active"><a href="${ctx}/payment/preUnionpayRecord/">银联扫码预下单列表</a></li>
		<shiro:hasPermission name="payment:preUnionpayRecord:edit"><li><a href="${ctx}/payment/preUnionpayRecord/form">银联扫码预下单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="preUnionpayRecord" action="${ctx}/payment/preUnionpayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>预下单单号：</label>
				<form:input path="prepayId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户订单号(商品号)：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>预下单单号</th>
				<th>商户号</th>
				<th>商户登录名</th>
				<th>商户公司名</th>
				<th>商户MCC码</th>
				<th>商户订单号</th>
				<th>支付次数限制</th>
				<th>支付截止时间</th>
				<th>单次支付金额</th>
				<th>成功支付总次数</th>
				<th>成功支付总金额</th>
				<th>商户请求时间</th>
				<shiro:hasPermission name="payment:preUnionpayRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="preUnionpayRecord">
			<tr>
				<td><a href="${ctx}/payment/preUnionpayRecord/form?id=${preUnionpayRecord.prepayId}">
					${preUnionpayRecord.prepayId}
				</a></td>
				<td>
					${preUnionpayRecord.merchantId}
				</td>
				<td>
					${preUnionpayRecord.merchantLoginName}
				</td>
				<td>
					${preUnionpayRecord.merchantCompany}
				</td>
				<td>
					${preUnionpayRecord.merchantMcc}
				</td>
				<td>
					${preUnionpayRecord.merchantOrderNo}
				</td>
				<td>
					${preUnionpayRecord.limitCount}
				</td>
				<td>
					<fmt:formatDate value="${preUnionpayRecord.qrExpireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatNumber value="${preUnionpayRecord.requestAmount}" pattern="￥0.00" />
				</td>
				<td>
					${preUnionpayRecord.successTimes}
				</td>
				<td>
					<fmt:formatNumber value="${preUnionpayRecord.successAmount}" pattern="￥0.00" />
				</td>
				<td>
					${preUnionpayRecord.requestDate}
				</td>
				<shiro:hasPermission name="payment:preUnionpayRecord:edit"><td>
    				<a href="${ctx}/payment/preUnionpayRecord/form?id=${preUnionpayRecord.id}">修改</a>
					<a href="${ctx}/payment/preUnionpayRecord/delete?id=${preUnionpayRecord.id}" onclick="return confirmx('确认要删除该银联扫码预下单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>