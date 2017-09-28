<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实名认证通道管理管理</title>
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
		<li class="active"><a href="${ctx}/route/certifyChannel/">产品列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/route/certifyChannel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>序号</th>
				<th>产品名称</th>
				<th>状态</th>
				<shiro:hasPermission name="route:certifyChannel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="product"  varStatus="i" >
			<tr>		 				 
		        <td>${i.count}</td>
			    <td><a href="${ctx}/route/certifyChannel/details?id=${product.id}">${product.name}</a></td>
			    <td>${product.status}</td>
			    <shiro:hasPermission name="route:certifyChannel:edit"><td>
   				<a href="${ctx}/route/certifyChannel/details?id=${product.id}">查看</a>	
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>