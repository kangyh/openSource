<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润申请管理</title>
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
		<%--<li class="active"><a href="${ctx}/profit/profit/agentProfitLog/">分润申请列表</a></li>--%>
		<%--<shiro:hasPermission name="profit:profit:agentProfitLog:edit"><li><a href="${ctx}/profit/profit/agentProfitLog/form">分润申请添加</a></li></shiro:hasPermission>--%>
	<%--</ul>--%>
	<form:form id="searchForm" modelAttribute="agentProfitLog" action="${ctx}/profit/profit/agentProfitLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>结算日期：</label>
				<input  id="settleBeginDate" name="settleBeginDate" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
						value="${agentProfitLog.settleBeginDate}"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input  id="settleEndDate" name="settleEndDate" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
						value="${agentProfitLog.settleEndDate}"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>分润序号</label>
				<form:input path="agentProfitId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>申请时间：</label>
				<input name="applyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentProfitLog.applyTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>申请金额：</label>
				<form:input path="profitBeginAmount" htmlEscape="false" maxlength="20" class="input-medium"/> -
				<form:input path="profitEndAmount" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>申请代理商编号</label>
				<form:input path="agentCode" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>申请代理商名称</label>
				<form:input path="agentName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>快递单号：</label>
				<form:input path="express" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>状态<%--（INICHK初审 RECHEK复审 REJECT审核拒绝 PROFIT分润中 FINISH已完成）--%>：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${agentProfitStatusList}" itemLabel="value" itemValue="name" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" >
		<thead>
			<tr>
				<th rowspan='2'>分润序号</th>
				<th rowspan='2'>结算日期</th>
				<th rowspan='2'>申请时间</th>
				<th rowspan='2'>申请金额</th>
				<th rowspan='2'>申请代理商编号</th>
				<th rowspan='2'>申请代理商名称</th>
				<th rowspan='2'>发票信息</th>
				<th rowspan='2'>快递信息</th>
				<th rowspan='2'>申请备注</th>
				<th rowspan='2'>审核时间</th>
				<th rowspan='2'>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentProfitLog">
			<tr >
				<td>
					${agentProfitLog.agentProfitId}
				</td>
				<td>
					${agentProfitLog.settleBeginDate}
				</td>
				<td>
					${agentProfitLog.applyTime}
				</td>
				<td>
					${agentProfitLog.profitAmount}
				</td>
				<td>
					${agentProfitLog.agentCode}
				</td>
				<td>
					${agentProfitLog.agentName}
				</td>
				<td>
					<a href="${agentProfitLog.invoices}">查看</a>
				</td>
				<td>
					${agentProfitLog.express}
				</td>
				<td>
					${agentProfitLog.applyRemark}
				</td>
				<td>
					<fmt:formatDate value="${agentProfitLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${agentProfitLog.status}
				</td>
				<%--<shiro:hasPermission name="profit:profit:agentProfitLog:edit"><td>
    				<a href="${ctx}/profit/profit/agentProfitLog/form?id=${agentProfitLog.id}">修改</a>
					<a href="${ctx}/profit/profit/agentProfitLog/delete?id=${agentProfitLog.id}" onclick="return confirmx('确认要删除该分润申请吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>