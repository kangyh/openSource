<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同管理</title>
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
		<li class="active"><a href="${ctx}/contract/contract/">合同列表</a></li>
		<shiro:hasPermission name="contract:contract:edit"><li><a href="${ctx}/contract/contract/form">合同添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="contract" action="${ctx}/contract/contract/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>合同编码：</label>
				<form:input path="contractCode" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>合同名称：</label>
				<form:input path="contractName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>合同平台：</label>
				<form:select path="byProject" class="input-xlarge ">
                    <form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('byProject')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>合同编码</th>
				<th>合同名称</th>
				<th>所属平台</th>
                <th>所属公司</th>
				<th>文件名</th>
				<th>更新人</th>
				<th>更新时间</th>
                <th>状态</th>
				<shiro:hasPermission name="contract:contract:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contract">
			<tr>
				<td>${contract.contractCode}</td>
				<td>${contract.contractName}</td>
				<td>${fns:getDictLabel(contract.byProject, 'ByProject', '')}</td>
                <td>${fns:getDictLabel(contract.byCompany, 'ByCompany', '')}</td>
				<td>${contract.fileName}</td>
                <td>${fns:getUserById(contract.updateBy.id).name}</td>
                <td><fmt:formatDate value="${contract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:if test="${contract.status=='ENABLE'}">启用</c:if>
					<c:if test="${contract.status=='DISABL'}">禁用</c:if>
				</td>
				<shiro:hasPermission name="contract:contract:edit"><td>
    				<a href="${ctx}/contract/contract/form?id=${contract.id}">修改</a>
					<a href="${ctx}/contract/contract/delete?id=${contract.id}" onclick="return confirmx('确认要删除该合同吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>