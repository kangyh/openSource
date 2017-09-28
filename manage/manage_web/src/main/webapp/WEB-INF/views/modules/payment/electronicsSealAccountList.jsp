<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>签宝账户管理管理</title>
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
		<li class="active"><a href="${ctx}/payment/electronicsSealAccount/">签宝账户管理列表</a></li>
		<%--<shiro:hasPermission name="payment:electronicsSealAccount:edit"><li><a href="${ctx}/payment/electronicsSealAccount/form">签宝账户管理添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="electronicsSealAccount" action="${ctx}/payment/electronicsSealAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户账号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>签宝账号：</label>
				<form:input path="sealAccountId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户账号</th>
				<th>商户名称</th>
				<th>签宝账号</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="payment:electronicsSealAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="electronicsSealAccount">
			<tr>
				<td>
					<%--<a href="${ctx}/payment/electronicsSealAccount/form?id=${electronicsSealAccount.id}">--%>
					${electronicsSealAccount.merchantId}
				<%--</a>--%>
				</td>
				<td>
					${electronicsSealAccount.merchantName}
				</td>
				<td>
					${electronicsSealAccount.sealAccountId}
				</td>
				<td>
					<fmt:formatDate value="${electronicsSealAccount.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${electronicsSealAccount.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${electronicsSealAccount.remark}
				</td>
				<shiro:hasPermission name="payment:electronicsSealAccount:edit"><td>
    				<a href="${ctx}/payment/electronicsSealAccount/form?id=${electronicsSealAccount.id}">修改</a>
					<a href="${ctx}/payment/electronicsSealAccount/delete?id=${electronicsSealAccount.id}" onclick="return confirmx('确认要删除该签宝账户管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>