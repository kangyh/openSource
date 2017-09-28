<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>技术签约管理</title>
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
		<li class="active"><a href="${ctx}/merchant/merchantAutographInfo/">技术签约列表</a></li>
		<%-- <shiro:hasPermission name="merchant:merchantAutographInfo:edit"><li><a href="${ctx}/merchant/merchantAutographInfo/form">技术签约添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantAutographInfo" action="${ctx}/merchant/merchantAutographInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" htmlEscape="false"  class="input-medium"/>
			</li>
			<li><label style="width:100px">商户公司名称：</label>
				<form:input path="merchantCompanyName" htmlEscape="false"  class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
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
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司名称</th>
				<th>产品名称</th>
				<%--<th>商户签约号</th>--%>
				<th>KEY</th>
				<th>状态</th>
				<shiro:hasPermission name="merchant:merchantAutographInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantAutographInfo">
			<tr>
				<td>${merchantAutographInfo.merchantId}</td>
				<td>${merchantAutographInfo.merchantLoginName}</td>
				<td>${merchantAutographInfo.merchantCompanyName}</td>
				<td>${merchantAutographInfo.productName}</td>
				<%--<td>${merchantAutographInfo.merchantSignNo}</td>--%>
				<td>${merchantAutographInfo.autographKey}</td>
				<td>${merchantAutographInfo.status}</td>
				<shiro:hasPermission name="merchant:merchantAutographInfo:edit"><td>
    				<c:if test="${merchantAutographInfo.status == '启用'}">
    					<a href="${ctx}/merchant/merchantAutographInfo/status?id=${merchantAutographInfo.id}&status=DISABL">禁用</a>
    				</c:if>
    				<c:if test="${merchantAutographInfo.status == '禁用'}">
	    				<a href="${ctx}/merchant/merchantAutographInfo/form?id=${merchantAutographInfo.id}">修改</a>
    					<a href="${ctx}/merchant/merchantAutographInfo/status?id=${merchantAutographInfo.id}&status=ENABLE">启用</a>
    				</c:if>
    				<a href="${ctx}/merchant/merchantAutographInfo/detail?id=${merchantAutographInfo.id}">详情</a>
					<%-- <a href="${ctx}/merchant/merchantAutographInfo/delete?id=${merchantAutographInfo.id}" onclick="return confirmx('确认要删除该技术签约吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>