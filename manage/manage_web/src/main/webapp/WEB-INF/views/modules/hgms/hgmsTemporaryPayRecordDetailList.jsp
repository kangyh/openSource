<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>临时代付明细管理</title>
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
		<li><a href="${ctx}/hgms/hgmsTemporaryPayRecord/">临时代付汇总列表</a></li>
		<li class="active"><a href="${ctx}/hgms/hgmsTemporaryPayRecordDetail/?transBatchNo=${hgmsTemporaryPayRecordDetail.transBatchNo}">临时代付明细列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsTemporaryPayRecordDetail" action="${ctx}/hgms/hgmsTemporaryPayRecordDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单批次号</th>
				<th>订单号</th>
				<th>商户名称</th>
				<th>子商户名称</th>
				<th>开户银行</th>
				<th>交易时间</th>
				<th>交易方式</th>
				<th>手续费</th>
				<th>交易金额</th>
				<th>错误描述</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsTemporaryPayRecordDetail">
			<tr>
				<td>
						${hgmsTemporaryPayRecordDetail.transBatchNo}
				</td>
				<td>
						${hgmsTemporaryPayRecordDetail.transId}
				</td>
				<td>
						${hgmsTemporaryPayRecordDetail.sourceMerchantName}
				</td>
				<td>
						${hgmsTemporaryPayRecordDetail.destMerchantName}
				</td>
				<td>
						${hgmsTemporaryPayRecordDetail.openbankName}
				</td>
				<td>
					<fmt:formatDate value="${hgmsTemporaryPayRecordDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${hgmsTemporaryPayRecordDetail.transWay}
				</td>
				<td>
					<fmt:formatNumber value="${hgmsTemporaryPayRecordDetail.feeAmount}" pattern="￥0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${hgmsTemporaryPayRecordDetail.payAmount}" pattern="￥0.00" />
				</td>
				<td>
						${hgmsTemporaryPayRecordDetail.errorMsg}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>