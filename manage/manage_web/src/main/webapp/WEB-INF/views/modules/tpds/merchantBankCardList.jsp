<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>merchant管理</title>
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
		<li class="active"><a href="#">银行卡列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantBankCard" action="${ctx}/merchant/merchantBankCard/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<!-- 	<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户账号</th>
				<th>银行名称</th>
				<th>银行账号</th>
				<th>开户银行名称</th>
				<th>开户名称</th>
				<th>签约类型</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantBankCard">
			<tr>
				<td>${merchantBankCard.merchantId}</td>
				<td>${merchantBankCard.bankName}</td>
				<td>${merchantBankCard.bankNo}</td>
				<td>${merchantBankCard.openBankName}</td>
				<td>${merchantBankCard.openName}</td>
				<td>
					<c:if test="${merchantBankCard.bankCardAuthType == 'TPDSTX'}">存管提现</c:if>
					<c:if test="${merchantBankCard.bankCardAuthType == 'REALCA'}">普通提现</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions">
		 <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>