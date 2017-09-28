<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>线上签约管理</title>
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
		<li class="active"><a href="${ctx}/merchant/onlineContractInfoLegal/">产品签约列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="onlineContractInfo" action="${ctx}/merchant/onlineContractInfoLegal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="userId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label style="width:100px">商户公司名称：</label>
				<form:input path="merchantCompanyName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${onlineContractInfo.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${onlineContractInfo.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
			</li>
			<li><label>审核状态：</label>
				<form:select id="legalAuditStatus" path="legalAuditStatus" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('routeStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
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
				<th>商户公司名称</th>
				<th>申请产品数量</th>
				<th>创建时间</th>
				<th>状态</th>
				<shiro:hasPermission name="merchant:onlineContractInfoLegal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="onlineContractInfo">
			<tr>
				<td>${onlineContractInfo.userId}</td>
				<td>${onlineContractInfo.merchantCompanyName}</td>
				<td>${onlineContractInfo.count}</td>
				<td><fmt:formatDate value="${onlineContractInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${onlineContractInfo.legalAuditStatus}</td>
				<shiro:hasPermission name="merchant:onlineContractInfoLegal:edit"><td>
					<c:if test="${onlineContractInfo.legalAuditStatus != '审核通过'}">
					<a style="cursor:pointer;" class="checkPass"   value-url="${ctx}/merchant/onlineContractInfoLegal/audit?batchNo=${onlineContractInfo.batchNo}&userId=${onlineContractInfo.userId}">审核</a>
                    </c:if>
					<a href="${ctx}/merchant/onlineContractInfoLegal/detail?batchNo=${onlineContractInfo.batchNo}&userId=${onlineContractInfo.userId}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>