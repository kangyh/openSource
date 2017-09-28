<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>邮件通知管理</title>
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
		<li class="active"><a href="${ctx}/notification/notificationEmail/">邮件通知列表</a></li>
		<shiro:hasPermission name="notification:notificationEmail:edit"><li><a href="${ctx}/notification/notificationEmail/form">邮件通知添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="notificationEmail" action="${ctx}/notification/notificationEmail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>邮箱</th>
				<th>人名</th>
				<th>状态</th>
				<th>创建人</th>
				<th>修改人</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<shiro:hasPermission name="notification:notificationEmail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="notificationEmail">
			<tr>
				<td>${notificationEmail.id}</td>
				<td>${notificationEmail.email}</td>
				<td>${notificationEmail.name}</td>
				<td>${notificationEmail.status}</td>
				<td>${notificationEmail.createUser}</td>
				<td>${notificationEmail.updateUser}</td>
				<td><fmt:formatDate value="${notificationEmail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${notificationEmail.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="notification:notificationEmail:edit"><td>
					<c:if test="${notificationEmail.status == '启用'}">
						<a href="${ctx}/notification/notificationEmail/status?id=${notificationEmail.id}&status=DISABL"  onclick="return confirmx('确认要禁用吗？', this.href)">禁用</a>
					</c:if>
					<c:if test="${notificationEmail.status == '禁用'}">
						<a href="${ctx}/notification/notificationEmail/status?id=${notificationEmail.id}&status=ENABLE" onclick="return confirmx('确认要启用吗？', this.href)">启用</a>
					</c:if>
    				<a href="${ctx}/notification/notificationEmail/form?id=${notificationEmail.id}">修改</a>
					<a href="${ctx}/notification/notificationEmail/delete?id=${notificationEmail.id}" onclick="return confirmx('确认要删除该邮件通知吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>