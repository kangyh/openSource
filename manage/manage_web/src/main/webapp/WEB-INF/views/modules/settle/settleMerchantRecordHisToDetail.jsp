<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>清结算管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
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
		<li class="active"><a href="${ctx}/settle/settleMerchantRecordHis/">商户结算历史单据详细</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="clearingChannelRecordHis" action="${ctx}/settle/settleMerchantRecordHis/toDetail?settleBath=${settleBath}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="settleBath" name="settleBath" type="hidden" value="${settleBath}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<ul class="ul-form">
		<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="window.history.go(-1);" value="返回"/></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>交易日期</th>
			<th>交易订单号</th>
			<th>交易类型</th>
			<th>币种</th>
			<th>实际支付金额</th>
			<th>清算流水号</th>
			<th>订单结算金额</th>
			<th>手续费金额</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clearingMerchantRecordHis">
			<tr>
				<td>${clearingMerchantRecordHis.merchantId}</td>
				<td><fmt:formatDate value="${clearingMerchantRecordHis.successTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${clearingMerchantRecordHis.transNo}</td>
				<td>${clearingMerchantRecordHis.transType}</td>
				<td>${clearingMerchantRecordHis.currency}</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecordHis.requestAmount}" pattern="￥0.0000" />
				</td>
				<td>${clearingMerchantRecordHis.settleNo}</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecordHis.settleAmountPlan}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecordHis.fee}" pattern="￥0.0000" />
				</td>
			</tr>

		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>