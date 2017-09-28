8<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费率操作日志管理</title>
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
		<li class="active"><a href="${ctx}/merchant/merchantRateLog/">费率操作日志列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantRateLog" action="${ctx}/merchant/merchantRateLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li><label>商户编码：</label>
                <form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
                    else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
            </li> 
            <li><label>产品名称：</label>
                <form:select path="productCode" class="input-xlarge" onchange="productChange(this.options[this.options.selectedIndex]);">
                    <form:option value="" label="全部"/>
                    <form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
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
			    <th>产品名称</th>
			    <th>操作人</th>
			    <th>操作时间</th>
			    <th>操作类型</th>
				<shiro:hasPermission name="merchant:merchantRateLog:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantRateLog">
			<tr>
			    <td>${merchantRateLog.merchantId}</td>
			    <td>${merchantRateLog.productName}</td>
			    <td>${merchantRateLog.operationUser}</td>
			    <td><fmt:formatDate value="${merchantRateLog.operationTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			    <td>${merchantRateLog.operationType}</td>
				<shiro:hasPermission name="merchant:merchantRateLog:view"><td>
    				<a href="${ctx}/merchant/merchantRateLog/detail?id=${merchantRateLog.id}">查看</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>