<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费率配置管理</title>
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

        function addRateForm(id,name,code) {
			location.href = "${ctx}/agent/rate/agentRate/form?agentId=" + id + "&agentName=" + name + "&agentCode=" + code;
			return;
        }
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li class="active"><a href="${ctx}/agent/rate/agentRate/">费率配置列表</a></li>--%>
		<%--&lt;%&ndash;<shiro:hasPermission name="agent:rate:agentRate:edit"><li><a href="${ctx}/agent/rate/agentRate/form">费率配置添加</a></li></shiro:hasPermission>&ndash;%&gt;--%>
	<%--</ul>--%>
	<form:form id="searchForm" modelAttribute="agentRateExt" action="${ctx}/agent/rate/agentRate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<div style="float:left">
				<label>代理商编号：${agentRateExt.agentCode}</label>  <label style="width: 200px">  </label>
				<label>代理商名称：${agentRateExt.agentName} </label>

				<form:input path="agentId" type="hidden" value="${agentRateExt.agentId}"/>
				<form:input path="agentName" type="hidden" value="${agentRateExt.agentName}"/>
				<form:input path="agentCode" type="hidden" value="${agentRateExt.agentCode}"/>
			</div>
			<div style="float:right;margin-right: 10px">
				<li><input type="button" class="btn btn-primary" onclick="history.go(-1)" value="返回"/> </li>
			</div>
		</ul>
	    <br/>
		<ul class="ul-form" style="background-color:#9F9FA0;height: 80px">
			<li><label>产品名称：</label>
				<form:select path="productCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${productList}" itemLabel="name" itemValue="code" htmlEscape="false"/>
					<%--<form:options items="${feeTypeList}" itemLabel="value" itemValue="name" htmlEscape="false"/>--%>
				</form:select>
			</li>
			<%--<li><label>交易总额：</label>--%>
				<%--<form:input path="transAmountBegin" htmlEscape="false" maxlength="11" class="input-medium"/>--%>
			<%--</li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li><input type="button" value="新增" class="btn btn-success" onclick="addRateForm('${agentRateExt.agentId}','${agentRateExt.agentName}','${agentRateExt.agentCode}')"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品名称</th>
				<th>交易总额区间</th>
				<th>分润百分比</th>
				<shiro:hasPermission name="agent:rate:agentRate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentRate">
			<tr>
				<td>
					${agentRate.productName}
				</a></td>
				<td>
					${agentRate.transAmountBegin}-${agentRate.transAmountEnd}万
				</td>
				<td>
					${agentRate.profitPercent}%
				</td>

				<shiro:hasPermission name="agent:rate:agentRate:edit"><td>
					<%--<a href="${ctx}/agent/rate/agentRate/form?id=${agentRate.id}">费率详情</a>--%>
    				<a href="${ctx}/agent/rate/agentRate/form?id=${agentRate.id}&agentId=${agentRateExt.agentId}&agentName=${agentRateExt.agentName}&agentCode=${agentRateExt.agentCode}">修改</a>
					<a href="${ctx}/agent/rate/agentRate/delete?id=${agentRate.id}&agentId=${agentRateExt.agentId}&agentName=${agentRateExt.agentName}&agentCode=${agentRateExt.agentCode}" onclick="return confirmx('确认要删除该费率配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>