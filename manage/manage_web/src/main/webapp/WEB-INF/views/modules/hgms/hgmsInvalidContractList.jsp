<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>无效合约管理</title>
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
        <shiro:hasPermission name="hgms:hgmsValidContract:view"><li><a href="${ctx}/hgms/hgmsValidContract">有效合约列表</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/hgms/hgmsInvalidContract/?merchantId=${invalidContract}">无效合约列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsInvalidContract" action="${ctx}/hgms/hgmsInvalidContract/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>合约编码</th>
			<th>商户编码</th>
			<th>上级商户公司名称</th>
			<th>公司名称</th>
			<th>合同名称</th>
			<th>业务名称</th>
			<th>服务项名称</th>
			<th>有效期开始时间</th>
			<th>有效期结束时间</th>
			<th>合约状态</th>
			<th>审核状态</th>
			<th>创建时间</th>
			<shiro:hasPermission name="hgms:hgmsInvalidContract:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsInvalidContract">
			<tr>
				<td>
						${hgmsInvalidContract.contractId}
				</td>
				<td>
						${hgmsInvalidContract.merchantId}
				</td>
				<td>
						${hgmsInvalidContract.superiorName}
				</td>
				<td>
						${hgmsInvalidContract.companyName}
				</td>
				<td>
						${hgmsInvalidContract.contractName}
				</td>
				<td>
						${hgmsInvalidContract.businessName}
				</td>
				<td>
						${hgmsInvalidContract.serviceName}
				</td>
				<td>
					<fmt:formatDate value="${hgmsInvalidContract.validityBeginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsInvalidContract.validityEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${hgmsInvalidContract.contractStatus}
				</td>
				<td>
						${hgmsInvalidContract.status}
				</td>
				<td>
					<fmt:formatDate value="${hgmsInvalidContract.enteringTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
                </td>
                <shiro:hasPermission name="hgms:hgmsInvalidContract:edit"><td>
                    <a href="${ctx}/hgms/hgmsInvalidContract/form?id=${hgmsInvalidContract.id}">详情</a>
                </td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>