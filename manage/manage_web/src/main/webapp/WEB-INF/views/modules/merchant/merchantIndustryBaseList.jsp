<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>行业范围新增</title>
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
		<li class="active"><a href="${ctx}/merchant/merchantIndustryBase/">行业范围维护</a></li>
		<shiro:hasPermission name="merchant:merchantIndustryBase:edit"><li><a href="${ctx}/merchant/merchantIndustryBase/form">行业范围添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantIndustryBase" action="${ctx}/merchant/merchantIndustryBase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户类别码：</label>
				<form:input path="mcc" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>行业大类：</label>
				<form:select  path="industryChildId" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getIndustry()}" itemLabel="industryChildName" itemValue="industryChildId" htmlEscape="false"/>
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
				<th>行业大类</th>
				<th>商户类别码（MCC）</th>
				<th>适用范围</th>
				<shiro:hasPermission name="merchant:merchantIndustryBase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantIndustryBase">
			<tr>
				<td>${merchantIndustryBase.industryChildName}</td>
				<td>${merchantIndustryBase.mcc}</td>
				<td>${merchantIndustryBase.industryDescribe}</td>
				<shiro:hasPermission name="merchant:merchantIndustryBase:edit"><td>
    				<a href="${ctx}/merchant/merchantIndustryBase/form?id=${merchantIndustryBase.id}">修改</a>
					<a href="${ctx}/merchant/merchantIndustryBase/delete?id=${merchantIndustryBase.id}" onclick="return confirmx('确认要删除该mcc基础数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>