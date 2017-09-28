<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
		<li class="active"><a href="${ctx}/merchant/productNetGroup/">产品组关联列表</a></li>
		<shiro:hasPermission name="merchant:productNetGroup:edit"><li><a href="${ctx}/merchant/productNetGroup/form">产品组关联增加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productNetGroup" action="${ctx}/merchant/productNetGroup/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>.net组</th>
				<th>创建人</th>
				<th>创建日期</th>
				<th>更新人</th>
				<th>更新日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="productNetGroup">
			<tr>
				<td>${productNetGroup.productCode}</td>
				<td>${productNetGroup.productName}</td>
				<td>${productNetGroup.netGroup}</td>
				<td>${productNetGroup.createName}</td>
				<td><fmt:formatDate value="${productNetGroup.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${productNetGroup.updateName}</td>
				<td><fmt:formatDate value="${productNetGroup.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<shiro:hasPermission name="merchant:productNetGroup:edit">
						<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/productNetGroup/form?id=${productNetGroup.id}">修改</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>