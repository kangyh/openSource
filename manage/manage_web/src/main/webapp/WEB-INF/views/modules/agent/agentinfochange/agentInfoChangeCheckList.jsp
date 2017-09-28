<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商信息变更管理</title>
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
	<form:form id="searchForm" modelAttribute="agentInfoChange" action="${ctx}/agent/agentinfochange/agentInfoChange/checkList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
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
			<li><label>创建时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${agentInfoChange.beginCreateTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${agentInfoChange.endCreateTime}"
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
				<shiro:hasPermission name="agent:agentinfochange:agentInfoChange:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentInfoChange">
			<tr>
				<td>
					${agentInfoChange.agentCode}
				</td>
				<td>
					${agentInfoChange.agentName}
				</td>
				<td>
						${agentInfoChange.agentLevel}
				</td>
				<td>
						${agentInfoChange.agentType}
				</td>
				<td>
					<fmt:formatDate value="${agentInfoChange.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${agentInfoChange.headName}
				</td>
				<td>
					${agentInfoChange.headPhone}
				</td>
				<td>
					${agentInfoChange.legalName}
				</td>
				<td>
					${agentInfoChange.legalPhone}
				</td>
				<shiro:hasPermission name="agent:agentinfochange:agentInfoChange:view"><td>
					<a href="${ctx}/agent/agentinfochange/agentInfoChange/checkDetail?id=${agentInfoChange.id}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>