<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户通道配置管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function search(){
			var flag = validatePreventInject($("#companyName").val(),"商户公司名称输入不合法!");
			return flag;
		}
	</script>
</head>
<body>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/route/merchantPayChannel/">商户列表</a></li>
		<%--<shiro:hasPermission name="route:merchantPayChannel:edit"><li><a href="${ctx}/route/merchantPayChannel/form">商户通道配置添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="merchant" action="${ctx}/route/merchantPayChannel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="userId" value="${merchant.userId}" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return search()" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户账号</th>
			<th>商户公司名称</th>
			<th>维系员工</th>
			<th>联系人</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchant">
			<tr>
				<td>${merchant.userId}</td>
				<td>${merchant.loginName}</td>
				<td>${merchant.companyName}</td>
				<td>${merchant.inchargerId}</td>
				<td>${merchant.contactor}</td>
				<td>${merchant.userStatus}</td>
				<td>
					<a href="${ctx}/route/merchantPayChannel/details?id=${merchant.userId}">查看</a>
					<shiro:hasPermission name="route:merchant:edit">
						<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/route/merchantPayChannel/form?merchantId=${merchant.userId}&merchantCompanyName=${merchant.companyName}&merchantLoginName=${merchant.loginName}" >通道配置</a>
					</shiro:hasPermission>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>