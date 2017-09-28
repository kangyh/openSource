<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算周期管理</title>
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
		<li class="active"><a href="${ctx}/merchant/settleCycleManage/">结算周期列表</a></li>
		<%-- <shiro:hasPermission name="merchant:settleCycleManage:edit"><li><a href="${ctx}/merchant/settleCycleManage/form">结算周期添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="settleCycleManage" action="${ctx}/merchant/settleCycleManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>结算类型：T+</label>
				<form:input path="settleType" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司名称</th>
				<th>产品名称</th>
				<th>结算类型</th>
				<th>状态</th>
				<shiro:hasPermission name="merchant:settleCycleManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleCycleManage">
			<tr>
				<td>${settleCycleManage.merchantId}</td>
				<td>${settleCycleManage.merchantLoginName}</td>
				<td>${settleCycleManage.merchantCompanyName}</td>
				<td>${settleCycleManage.productName}</td>
				<td>T+${settleCycleManage.settleType}</td>
				<td>${settleCycleManage.status}</td>
				<shiro:hasPermission name="merchant:settleCycleManage:edit"><td>
    				<a href="${ctx}/merchant/settleCycleManage/form?id=${settleCycleManage.id}">修改</a>
    				<c:if test="${settleCycleManage.status == '启用'}">
    					<a href="${ctx}/merchant/settleCycleManage/status?id=${settleCycleManage.id}&status=DISABL">禁用</a>
    				</c:if>
    				<c:if test="${settleCycleManage.status == '禁用'}">
    					<a href="${ctx}/merchant/settleCycleManage/status?id=${settleCycleManage.id}&status=ENABLE">启用</a>
    				</c:if>
    				<a href="${ctx}/merchant/settleCycleManage/detail?id=${settleCycleManage.id}">详情</a>
					<%-- <a href="${ctx}/merchant/settleCycleManage/delete?id=${settleCycleManage.id}" onclick="return confirmx('确认要删除该结算周期吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>