<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品通道管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnClear").on("click",function(){
				//console.log("11");
				$("#name").val("");
				$("#status").val("");
				$("#searchForm").submit();
			});	
			//$("#contentTable tr:eq(15)").hide();
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
		<li class="active"><a href="${ctx}/route/productChannel/">产品列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/route/productChannel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li><label style="width:150px">产品名称：</label>
		    	<form:input path="name" htmlEscape="false" maxlength="100" value="${product.name}" class="input-medium" />
	        </li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清空" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:800px">
		<thead>
			<tr>
			    <th>序号</th>
				<th>产品名称</th>
				<th>状态</th>
				<shiro:hasPermission name="route:productChannel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="product" varStatus="i">
			<tr>
			    <td>${i.count}</td>
				<td><a href="${ctx}/route/productChannel/details?id=${product.id}">${product.name}</a></td>
				<td>${product.status}</td>
				<shiro:hasPermission name="route:productChannel:edit"><td>
    				<a href="${ctx}/route/productChannel/details?id=${product.id}">查看</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>