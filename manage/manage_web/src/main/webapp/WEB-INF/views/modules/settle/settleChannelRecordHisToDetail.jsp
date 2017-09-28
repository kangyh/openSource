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
		<li class="active"><a href="${ctx}/settle/settleChannelRecordHis/">通道历史结算单据详细</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="clearingChannelRecordHis" action="${ctx}/settle/settleChannelRecordHis/toDetail?settleBath=${settleBath}" method="post" class="breadcrumb form-search">
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
			<th>通道合作方</th>
			<th>支付通道类型</th>
			<th>交易类型</th>
			<th>交易日期</th>
			<th>币种</th>
			<th>支付单号</th>
			<th>原支付单号</th>
			<th>实际支付金额</th>
			<th>清算日期</th>
			<th>清算流水号</th>
			<th>交易成本</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clearingChannelRecordHis">
			<tr>
				<td>${clearingChannelRecordHis.channelName}</td>
				<td>${clearingChannelRecordHis.channelType}</td>
				<td>${clearingChannelRecordHis.transType}</td>
				<td><fmt:formatDate value="${clearingChannelRecordHis.payTime}" type="both" pattern="yyyy-MM-dd"/></td>
				<td>${clearingChannelRecordHis.currency}</td>
				<td>${clearingChannelRecordHis.paymentId}</td>
				<td>${clearingChannelRecordHis.paymentIdOld}</td>
				<td>
					<fmt:formatNumber value="${clearingChannelRecordHis.successAmount}" pattern="￥0.0000" />
				</td>
				<td><fmt:formatDate value="${clearingChannelRecordHis.settleTime}" type="both" pattern="yyyy-MM-dd"/></td>
				<td>${clearingChannelRecordHis.settleNo}</td>
				<td>
					<fmt:formatNumber value="${clearingChannelRecordHis.costAmount}" pattern="￥0.0000" />
				</td>
			</tr>

		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>