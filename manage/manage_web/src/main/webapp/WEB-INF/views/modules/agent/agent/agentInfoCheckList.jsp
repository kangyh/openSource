<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商信息审核管理</title>
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
	<%--<ul class="nav nav-tabs">--%>
		<%--<li class="active"><a href="${ctx}/agent/agent/agentInfo/">代理商列表</a></li>--%>
		<%--<shiro:hasPermission name="agent:agent:agentInfo:edit"><li><a href="${ctx}/agent/agent/agentInfo/form">代理商添加</a></li></shiro:hasPermission>--%>
	<%--</ul>--%>
	<form:form id="searchForm" modelAttribute="agentInfo" action="${ctx}/agent/agent/agentInfo/checkList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>代理商编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="10" class="input-medium"/>
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
			<li><label>创建时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentInfo.beginCreateTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentInfo.endCreateTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>代理商编号</th>
				<th>代理商名称</th>
				<th>代理商等级</th>
				<th>类型</th>
				<th>加入时间</th>
				<th>负责人姓名</th>
				<th>负责人手机号</th>
				<th>法人姓名</th>
				<th>法人手机号</th>
				<th>操作</th>
				<%--<th>操作</th>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentInfo">
			<tr>
				<td>
					${agentInfo.agentCode}
				</td>
				<td>
						${agentInfo.agentName}
				</td>
				<td>
					${agentInfo.agentLevel}
				</td>
				<td>
						${agentInfo.agentType}
				</td>
				<td>
					<fmt:formatDate value="${agentInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${agentInfo.headName}
				</td>
				<td>
						${agentInfo.headPhone}
				</td>
				<td>
						${agentInfo.legalName}
				</td>
				<td>
						${agentInfo.legalPhone}
				</td>
				<shiro:hasPermission name="agent:agent:agentInfo:view">
				<td>
					<a href="${ctx}/agent/agent/agentInfo/checkDetail?id=${agentInfo.id}">详情</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>