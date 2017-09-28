<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商商户管理</title>
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
		<li class="active"><a href="${ctx}/agent/agentmerchant/agentMerchant/">代理商商户列表</a></li>
		<shiro:hasPermission name="agent:agentmerchant:agentMerchant:edit"><li><a href="${ctx}/agent/agentmerchant/agentMerchant/form">代理商商户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="agentMerchant" action="${ctx}/agent/agentmerchant/agentMerchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>加入时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentMerchant.beginCreateTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentMerchant.endCreateTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>商户编号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>代理商编号：</label>
				<form:input path="agentCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>代理商名称：</label>
				<form:input path="agentName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>负责人姓名：</label>
				<form:input path="headName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>负责人手机号：</label>
				<form:input path="headPhone" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>法人姓名：</label>
				<form:input path="legalName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>法人手机号：</label>
				<form:input path="legalPhone" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="agentMerchantStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${agentMerchantStatusList}" itemLabel="value" itemValue="name" htmlEscape="false"/>
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
				<th>商户编号</th>
				<th>商户名称</th>
				<th>商户类型</th>
				<th>代理商编号</th>
				<th>代理商名称</th>
				<th>代理商等级</th>
				<th>加入时间</th>
				<th>状态</th>
				<shiro:hasPermission name="agent:agentmerchant:agentMerchant:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentMerchant">
			<tr>
				<%--<td><a href="${ctx}/agent/agentmerchant/agentMerchant/form?id=${agentMerchant.id}">
						${agentMerchant.merchantId}
				</a></td>--%>
				<td>
						${agentMerchant.merchantId}
				</td>
				<td>
						${agentMerchant.merchantName}
				</td>
				<td>
						${agentMerchant.merchantType}
				</td>
				<td>
						${agentMerchant.agentCode}
				</td>
				<td>
						${agentMerchant.agentName}
				</td>
				<td>
						${agentMerchant.agentLevel}
				</td>
				<td>
					<fmt:formatDate value="${agentMerchant.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${agentMerchant.agentMerchantStatus}
				</td>
				<shiro:hasPermission name="agent:agentmerchant:agentMerchant:view"><td>
						<a href="${ctx}/agent/agentmerchant/agentMerchant/detail?id=${agentMerchant.id}">详情</a>
    				<%--<a href="${ctx}/agent/agentmerchant/agentMerchant/form?id=${agentMerchant.id}">修改</a>--%>
					<%--<a href="${ctx}/agent/agentmerchant/agentMerchant/delete?id=${agentMerchant.id}" onclick="return confirmx('确认要删除该代理商商户吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>