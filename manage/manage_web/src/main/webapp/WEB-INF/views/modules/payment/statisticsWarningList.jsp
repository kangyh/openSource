<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户成功率监控管理</title>
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
		<li class="active"><a href="${ctx}/payment/statisticsWarning/">商户成功率监控列表</a></li>
		<li><a href="${ctx}/payment/statisticsWarning/form">商户成功率监控添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="statisticsWarning" action="${ctx}/payment/statisticsWarning/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>商户公司：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>商户状态</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('WarningType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>成功率：</label>
				<form:input path="rate" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司</th>
				<th>商户状态</th>
				<th>成功率</th>
				<th>备注</th>
				<shiro:hasPermission name="payment:statisticsWarning:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="statisticsWarning">
			<tr>
				<td><a href="${ctx}/payment/statisticsWarning/form?id=${statisticsWarning.id}">
					${statisticsWarning.merchantId}
				</a></td>
				<td>
					${statisticsWarning.merchantLoginName}
				</td>
				<td>
					${statisticsWarning.merchantCompany}
				</td>
				<td>
					${fns:getDictLabel(statisticsWarning.status, 'WarningType', '')}
				</td>
				<td>
					${statisticsWarning.rate}
				</td>
				<td>
					${statisticsWarning.remark}
				</td>
				<shiro:hasPermission name="payment:statisticsWarning:edit"><td>
    				<a href="${ctx}/payment/statisticsWarning/form?id=${statisticsWarning.id}">修改</a>
					<a href="${ctx}/payment/statisticsWarning/delete?id=${statisticsWarning.id}" onclick="return confirmx('确认要删除该商户成功率监控吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>